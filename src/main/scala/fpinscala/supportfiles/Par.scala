package fpinscala.supportfiles

import java.util.concurrent.{ExecutorService, Future}

import fpinscala.supportfiles.Par.Par

object Par {
  type Par[A] = ExecutorService => Future[A]

  def unit[A](a: => A): Par[A] = ???
  def get[A](a: Par[A]): A = ???

  def map2[A, B, C](a: Par[A], b: Par[B])(op: (A, B) => C): Par[C] = unit(op(get(a), get(b)))

}

object Examples {
  def sum(ints: IndexedSeq[Int]): Par[Int] =
    if (ints.lengthCompare(1) <= 0) {
      Par.unit(ints.headOption.getOrElse(0))
    } else {
      val (l, r) = ints.splitAt(ints.length / 2)
      Par.map2(sum(l), sum(r))(_ + _)
    }
}
