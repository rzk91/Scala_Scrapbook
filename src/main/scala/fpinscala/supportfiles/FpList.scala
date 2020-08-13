package fpinscala.supportfiles

import scala.annotation.tailrec

sealed trait FpList[+A]
case object FpNil extends FpList[Nothing]
case class Cons[+A](head: A, tail: FpList[A]) extends FpList[A]

object FpList {
  def sum[A](as: FpList[A])(implicit num: Numeric[A]): A = as match {
    case FpNil => num.zero
    case Cons(h, t) => num.plus(h, sum(t))
  }

  def sum2[A](as: FpList[A])(implicit num: Numeric[A]): A = foldLeft(as, num.zero)(num.plus)

  def product[A](as: FpList[A])(implicit num: Numeric[A]): A = as match {
    case FpNil => num.one
    case Cons(0.0, _) => num.zero
    case Cons(h, t) => num.times(h, product(t))
  }

  def product2[A](as: FpList[A])(implicit num: Numeric[A]): A = foldLeft(as, num.one)(num.times)

  def apply[A](as: A*): FpList[A] = if (as.isEmpty) FpNil else Cons(as.head, apply(as.tail: _*))

  def tail[A](as: FpList[A]): FpList[A] = as match {
    case FpNil => throw new Exception("Empty list")
    case _ => drop(as, 1)
  }

  def setHead[A](as: FpList[A], h: A): FpList[A] = as match {
    case FpNil => throw new Exception("Cannot set head of empty list")
    case Cons(_, t) => Cons(h, t)
  }

  @tailrec
  def drop[A](as: FpList[A], n: Int): FpList[A] =
    if (n <= 0) as
    else {
      as match {
        case FpNil => FpNil
        case Cons(_, t) => drop(t, n-1)
      }
    }

  @tailrec
  def dropWhile[A](as: FpList[A])(f: A => Boolean): FpList[A] = as match {
    case FpNil => throw new Exception("Empty list")
    case Cons(h, t) if f(h) => dropWhile(t)(f)
    case _ => as
  }

  def append[A](a1: FpList[A], a2: FpList[A]): FpList[A] = a1 match {
    case FpNil => a2
    case Cons(h, t) => Cons(h, append(t, a2))
  }

  def append2[A](a1: FpList[A], a2: FpList[A]): FpList[A] = foldLeft(reverse(a1), a2)((acc, a) => Cons(a, acc))

  def append3[A](a1: FpList[A], a2: FpList[A]): FpList[A] = foldRight(a1, a2)(Cons(_, _))

  def init[A](as: FpList[A]): FpList[A] = as match {
    case FpNil => throw new Exception("Empty list")
    case Cons(_, FpNil) => FpNil
    case Cons(h, t) => Cons(h, init(t))
  }

  def init2[A](as: FpList[A]): FpList[A] = {
    import collection.mutable.ListBuffer
    val b = new ListBuffer[A]

    @tailrec
    def loop(acc: FpList[A]): FpList[A] = acc match {
      case FpNil => throw new Exception("Empty list")
      case Cons(_, FpNil) => FpList(b.toList: _*)
      case Cons(h, t) => b += h; loop(t)
    }

    loop(as)
  }

  def foldRight[A, B](as: FpList[A], z: B)(op: (A, B) => B): B = as match {
    case FpNil => z
    case Cons(h, t) => op(h, foldRight(t, z)(op))
  }

  def foldRight2[A, B](as: FpList[A], z: B)(op: (A, B) => B): B = foldLeft(reverse(as), z)((b, a) => op(a, b))

  @tailrec
  def foldLeft[A, B](as: FpList[A], z: B)(op: (B, A) => B): B = as match {
    case FpNil => z
    case Cons(h, t) => foldLeft(t, op(z, h))(op)
  }

  def foldLeft2[A, B](as: FpList[A], outerZ: B)(op: (B, A) => B): B = {
    type BtoB = B => B
    def innerZ: BtoB = (b: B) => b
    def opDelayer: (A, BtoB) => BtoB = (a: A, delay: BtoB) => (b: B) => delay(op(b, a))

    def go: BtoB = foldRight(as, innerZ)(opDelayer)

    go(outerZ)
  }

  def length[A](as: FpList[A]): Int = foldLeft(as, 0)((acc, _) => acc + 1)

  def reverse[A](as: FpList[A]): FpList[A] = foldLeft(as, FpNil: FpList[A])((acc, a) => Cons(a, acc))

  def flatten[A](lists: FpList[FpList[A]]): FpList[A] = lists match {
    case FpNil => FpNil
    case Cons(h, t) => foldLeft(t, h)(append)
  }

  def flatten2[A](lists: FpList[FpList[A]]): FpList[A] = foldRight(lists, FpNil: FpList[A])(append)

  def map[A, B](l: FpList[A])(op: A => B): FpList[B] = {
    val b = new collection.mutable.ListBuffer[B]
    @tailrec
    def loop(as: FpList[A]): Unit = as match {
      case FpNil => FpNil
      case Cons(h, t) => b += op(h); loop(t)
    }
    loop(l)
    FpList(b.toList: _*)
  }

  def map2[A, B](l: FpList[A])(op: A => B): FpList[B] = foldRight(l, FpNil: FpList[B])((a, acc) => Cons(op(a), acc))

  def flatMap[A, B](l: FpList[A])(op: A => FpList[B]): FpList[B] = flatten(map(l)(op))

  def filter[A](l: FpList[A])(op: A => Boolean): FpList[A] = flatMap(l)(a => if(op(a)) FpList(a) else FpNil)

  def zipWith[A, B, C](l1: FpList[A], l2: FpList[B])(op: (A, B) => C): FpList[C] = (l1, l2) match {
    case (FpNil, _) => FpNil
    case (_, FpNil) => FpNil
    case (Cons(h1, t1), Cons(h2, t2)) => Cons(op(h1, h2), zipWith(t1, t2)(op))
  }

  @tailrec
  def hasSubsequence[A](mainList: FpList[A], subList: FpList[A]): Boolean = (mainList, subList) match {
      case (FpNil, s) => s == FpNil
      case (Cons(h1, t1), Cons(h2, t2)) if h1 == h2 => hasSubsequence(t1, t2)
      case (Cons(_, t1), _) => hasSubsequence(t1, subList)
    }
}
