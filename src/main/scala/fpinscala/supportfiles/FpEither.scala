package fpinscala.supportfiles

sealed trait FpEither[+E, +A] {
  def map[B](op: A => B): FpEither[E, B] = this match {
    case FpLeft(v) => FpLeft(v)
    case FpRight(v) => FpRight(op(v))
  }

  def flatMap[EE >: E, B](op: A => FpEither[EE, B]): FpEither[EE, B] = this match {
    case FpLeft(v) => FpLeft(v)
    case FpRight(v) => op(v)
  }

  def orElse[EE >: E, B >: A](op: => FpEither[EE, B]): FpEither[EE, B] = this match {
    case FpLeft(_) => op
    case FpRight(v) => FpRight(v)
  }

  def map2[EE >: E, B, C](b: FpEither[EE, B])(op: (A, B) => C): FpEither[EE, C] = for {
    x <- this
    y <- b
  } yield op(x, y)
}

case class FpLeft[+E](value: E) extends FpEither[E, Nothing]
case class FpRight[+A](value: A) extends FpEither[Nothing, A]

object FpEither {
  def sequence[E, A](l: List[FpEither[E, A]]): FpEither[E, List[A]] = traverse(l)(x => x)

  def traverse[E, A, B](l: List[A])(op: A => FpEither[E, B]): FpEither[E, List[B]] = l match {
    case Nil => FpRight(Nil)
    case h :: t => op(h).map2(traverse(t)(op))(_ :: _)
  }

  def traverse_1[E, A, B](l: List[A])(op: A => FpEither[E, B]): FpEither[E, List[B]] =
    l.foldRight[FpEither[E, List[B]]](FpRight(Nil))((a, acc) => op(a).map2(acc)(_ :: _))
}
