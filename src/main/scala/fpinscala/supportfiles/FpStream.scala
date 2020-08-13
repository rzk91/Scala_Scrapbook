package fpinscala.supportfiles

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer
import FpStream._

sealed trait FpStream [+A] {
  def toList: List[A] = {
    @tailrec
    def loop(left: FpStream[A], acc: List[A]): List[A] = left match {
      case StreamCons(h, t) => loop(t(), h() :: acc)
      case _ => acc
    }
    loop(this, List()).reverse
  }

  @tailrec
  final def drop(n: Int): FpStream[A] = this match {
      case StreamCons(_, t) if n > 0 => t().drop(n - 1)
      case _ => this
  }

  def take(n: Int): FpStream[A] = this match {
    case StreamCons(h, t) if n > 1 => cons(h(), t().take(n - 1))
    case StreamCons(h, _) if n == 1 => cons(h(), empty)
    case _ => empty
  }

  def take_1(n: Int): FpStream[A] = {
    val b = new ListBuffer[A]
    @tailrec
    def loop(left: => FpStream[A], n: Int): FpStream[A] =
      if (n <= 0) {
        apply(b: _*)
      } else {
        left match {
          case StreamCons(h, t) => b += h(); loop(t(), n - 1)
          case _ => this
        }
      }
    loop(this, n)
  }

  def takeWhile(op: A => Boolean): FpStream[A] = this match {
    case StreamCons(h, t) if op(h()) => cons(h(), t().takeWhile(op))
    case _ => empty
  }

  def foldRight[B](z: B)(op: (A, => B) => B): B = this match {
    case StreamCons(h, t) => op(h(), t().foldRight(z)(op))
    case _ => z
  }

  def exists(op: A => Boolean): Boolean = foldRight(false)((a, acc) => op(a) || acc)

  def forAll(op: A => Boolean): Boolean = foldRight(true)((a, acc) => op(a) && acc)

  def takeWhile_foldRight(op: A => Boolean): FpStream[A] = foldRight(empty[A])((a, acc) =>
    if (op(a)) cons(a, acc) else empty
  )

  def headOption: Option[A] = this match {
    case StreamCons(h, _) => Some(h())
    case _ => None
  }

  def headOption_foldRight: Option[A] = foldRight(None: Option[A])((a, _) => Some(a))

  def map[B](op: A => B): FpStream[B] = foldRight(empty[B])((a, acc) => cons(op(a), acc))

  def filter(op: A => Boolean): FpStream[A] = foldRight(empty[A])((a, acc) => if (op(a)) cons(a, acc) else acc)

  def find(op: A => Boolean): Option[A] = filter(op).headOption

  def append[AA >: A](s: => FpStream[AA]): FpStream[AA] = foldRight(s)((a, acc) => cons(a, acc))

  def flatMap[B](op: A => FpStream[B]): FpStream[B] = foldRight(empty[B])((a, acc) => op(a) append acc)

  def flatMap_1[B](op: A => FpStream[B]): FpStream[B] = map(op).foldRight(empty[B])(_ append _)

  def map_usingUnfold[B](op: A => B): FpStream[B] = unfold(this) {
    case StreamCons(h, t) => Some((op(h()), t()))
    case _ => None
  }

  def take_usingUnfold(n: Int): FpStream[A] = unfold((n, this)) {
    case (n, StreamCons(h, t)) if n > 1 => Some((h(), (n - 1, t())))
    case (1, StreamCons(h, _)) => Some((h(), (0, empty)))
    case _ => None
  }

  def takeWhile_usingUnfold(op: A => Boolean): FpStream[A] = unfold(this) {
    case StreamCons(h, t) if op(h()) => Some((h(), t()))
    case _ => None
  }

  def zipWith[B, C](s2: FpStream[B])(op: (A, B) => C): FpStream[C] = unfold((this, s2)) {
    case (StreamCons(h1, t1), StreamCons(h2, t2)) => Some((op(h1(), h2()), (t1(), t2())))
    case _ => None
  }

  def zipWithAll[B, C](s2: FpStream[B])(op: (Option[A], Option[B]) => C): FpStream[C] = unfold((this, s2)) {
    case (StreamCons(h1, t1), FpEmpty) => Some((op(Some(h1()), Option.empty[B]), (t1(), empty[B])))
    case (FpEmpty, StreamCons(h2, t2)) => Some((op(Option.empty[A], Some(h2())), (empty[A], t2())))
    case (StreamCons(h1, t1), StreamCons(h2, t2)) => Some((op(Some(h1()), Some(h2())), (t1(), t2())))
    case _ => None
  }

  def zipAll[B](s2: FpStream[B]): FpStream[(Option[A], Option[B])] = zipWithAll(s2)((_, _))

  def startsWith[AA >: A](s: FpStream[AA]): Boolean = zipAll(s).takeWhile(_._2.isDefined).forAll {
    case (a, aa) => a == aa
  }

  def tails: FpStream[FpStream[A]] = unfold(this) {
    case s: StreamCons[A] => Some((s, s.drop(1)))
    case _ => None
  }.append(FpStream(empty))

  def hasSubsequence[AA >: A](s: FpStream[AA]): Boolean = tails.exists(_.startsWith(s))

  def scanRight[B](z: B)(op: (A, => B) => B): FpStream[B] = foldRight((z, FpStream(z)))(
    (a, acc) => {
      lazy val acc0 = acc
      val newZ = op(a, acc0._1)
      (newZ, cons(newZ, acc0._2))
    }
  )._2

}
case object FpEmpty extends FpStream[Nothing]
case class StreamCons[+A](h: () => A, t: () => FpStream[A]) extends FpStream[A]

object FpStream {
  def cons[A](hd: => A, tl: => FpStream[A]): FpStream[A] = {
    lazy val head = hd
    lazy val tail = tl
    StreamCons(() => head, () => tail)
  }

  def empty[A]: FpStream[A] = FpEmpty

  def apply[A](as: A*): FpStream[A] =
    if (as.isEmpty) empty else cons(as.head, apply(as.tail: _*))

  def ones: FpStream[Int] = cons(1, ones)

  def constant[A](a: A): FpStream[A] = {
    lazy val tail: FpStream[A] = StreamCons(() => a, () => tail)
    tail
  }

  def from(n: Int): FpStream[Int] = cons(n, from(n + 1))

  def fibs: FpStream[Int] = {
    def loop(n1: Int, n2: Int): FpStream[Int] = {
      cons(n1, loop(n2, n1 + n2))
    }
    loop(0, 1)
  }

  def unfold[A, S](z: S)(op: S => Option[(A, S)]): FpStream[A] = op(z) match {
    case Some((a, s)) => cons(a, unfold(s)(op))
    case None => empty
  }

  def ones_usingUnfold: FpStream[Int] = unfold(1)(_ => Some((1, 1)))

  def constant_usingUnfold[A](a: A): FpStream[A] = unfold(a)(_ => Some((a, a)))

  def from_usingUnfold(n: Int): FpStream[Int] = unfold(n)(v => Some((v + 1, v)))

  def fibs_usingUnfold: FpStream[Int] = unfold((0, 1)) {
    case (n1, n2) => Some((n1, (n2, n1 + n2)))
  }
}
