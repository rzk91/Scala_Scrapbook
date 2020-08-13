package fpinscala.supportfiles

sealed trait FpOption[+A] {
  def map[B](op: A => B): FpOption[B] = this match {
    case FpSome(a) => FpSome(op(a))
    case FpNone => FpNone
  }

  def getOrElse[B >: A](default: => B): B = this match {
    case FpSome(a) => a
    case FpNone => default
  }

  def flatMap[B](op: A => FpOption[B]): FpOption[B] = map(op).getOrElse(FpNone)

  def orElse[B >: A](ob: => FpOption[B]): FpOption[B] = map(FpSome(_)).getOrElse(ob)

  def filter(op: A => Boolean): FpOption[A] = flatMap(a => if (op(a)) FpSome(a) else FpNone)
}
case class FpSome[A](get: A) extends FpOption[A]
case object FpNone extends FpOption[Nothing]

object FpOption {
  def mean(xs: Seq[Double]): FpOption[Double] = if (xs.isEmpty) FpNone else FpSome(xs.sum/xs.size)

  def variance(xs: Seq[Double]): FpOption[Double] = mean(xs).flatMap(m => mean(xs.map(x => math.pow(x - m, 2))))

  def map2[A, B, C](a: FpOption[A], b: FpOption[B])(op: (A, B) => C): FpOption[C] = a.flatMap(v1 => b.map(v2 => op(v1, v2)))

  def map2_1[A, B, C](a: FpOption[A], b: FpOption[B])(op: (A, B) => C): FpOption[C] = for {
    v1 <- a
    v2 <- b
  } yield op(v1, v2)

  def sequence[A](l: List[FpOption[A]]): FpOption[List[A]] = l match {
    case Nil => FpSome(Nil)
    case h :: t => h.flatMap(v1 => sequence(t).map(v1 :: _))
  }

  def sequence2[A](l: List[FpOption[A]]): FpOption[List[A]] =
    l.foldRight[FpOption[List[A]]](FpSome(Nil))((a, acc) => map2(a, acc)(_ :: _))

  def sequenceUsingTraverse[A](l: List[FpOption[A]]): FpOption[List[A]] = traverse(l)(x => x)

  def traverse[A, B](l: List[A])(op: A => FpOption[B]): FpOption[List[B]] =
    l.foldRight[FpOption[List[B]]](FpSome(Nil))((a, acc) => map2(op(a), acc)(_ :: _))

  def traverseWithPatternMatching[A, B](l: List[A])(op: A => FpOption[B]): FpOption[List[B]] = l match {
    case Nil => FpSome(Nil)
    case h :: t => map2(op(h), traverseWithPatternMatching(t)(op))(_ :: _)
  }
}