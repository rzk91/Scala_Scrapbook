package fpinscala.supportfiles

import scala.util.Try

sealed trait FpEither[+E, +A] {

  def map[B](op: A => B): FpEither[E, B] = this match {
    case FpLeft(v)  => FpLeft(v)
    case FpRight(v) => FpRight(op(v))
  }

  def flatMap[EE >: E, B](op: A => FpEither[EE, B]): FpEither[EE, B] = this match {
    case FpLeft(v)  => FpLeft(v)
    case FpRight(v) => op(v)
  }

  def orElse[EE >: E, B >: A](op: => FpEither[EE, B]): FpEither[EE, B] = this match {
    case FpLeft(_)  => op
    case FpRight(v) => FpRight(v)
  }

  def map2[EE >: E, B, C](b: FpEither[EE, B])(op: (A, B) => C): FpEither[EE, C] =
    for {
      x <- this
      y <- b
    } yield op(x, y)
}

case class FpLeft[+E](value: E) extends FpEither[E, Nothing]
case class FpRight[+A](value: A) extends FpEither[Nothing, A]

object FpEither {
  def sequence[E, A](l: List[FpEither[E, A]]): FpEither[E, List[A]] = traverse(l)(identity)

  def traverse[E, A, B](l: List[A])(op: A => FpEither[E, B]): FpEither[E, List[B]] = l match {
    case Nil    => FpRight(Nil)
    case h :: t => op(h).map2(traverse(t)(op))(_ :: _)
  }

  def traverse_1[E, A, B](l: List[A])(op: A => FpEither[E, B]): FpEither[E, List[B]] =
    l.foldRight[FpEither[E, List[B]]](FpRight(Nil))((a, acc) => op(a).map2(acc)(_ :: _))
}

object FpTry {

  implicit class TryOps[A](val target: Try[A]) extends AnyVal {
    def exists(f: A => Boolean): Boolean = target.isSuccess && f(target.get)
    def contains[B >: A](b: B): Boolean = target.isSuccess && b == target.get

    def map2[B, C](b: Try[B])(f: (A, B) => C): Try[C] =
      for {
        x <- target
        y <- b
      } yield f(x, y)
  }

  def traverse[A, B](l: List[A])(f: A => Try[B]): Try[List[B]] =
    l.foldRight[Try[List[B]]](Try(Nil))((a, acc) => f(a).map2(acc)(_ :: _))
}
