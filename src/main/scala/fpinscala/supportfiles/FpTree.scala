package fpinscala.supportfiles

sealed trait FpTree[+A]
case class Leaf[A](value: A) extends FpTree[A]
case class Branch[A](left: FpTree[A], right: FpTree[A]) extends FpTree[A]

object FpTree {
  def size[A](tree: FpTree[A]): Int = tree match {
    case Leaf(_) => 1
    case Branch(l, r) => size(l) + size(r)
  }

  def maximum(tree: FpTree[Int]): Int = tree match {
    case Leaf(v) => v
    case Branch(l, r) => maximum(l) max maximum(r)
  }

  def depth[A](tree: FpTree[A]): Int = tree match {
    case Leaf(_) => 0
    case Branch(l, r) => 1 + (depth(l) max depth(r))
  }

  def map[A, B](tree: FpTree[A])(op: A => B): FpTree[B] = tree match {
    case Leaf(v) => Leaf(op(v))
    case Branch(l, r) => Branch(map(l)(op), map(r)(op))
  }

  def fold[A, B](tree: FpTree[A])(opl: A => B)(opb: (B, B) => B): B = tree match {
    case Leaf(v) => opl(v)
    case Branch(l, r) => opb(fold(l)(opl)(opb), fold(r)(opl)(opb))
  }

  def size2[A](tree: FpTree[A]): Int = fold(tree)(_ => 1)(_ + _)

  def maximum2(tree: FpTree[Int]): Int = fold(tree)(a => a)((a, b) => a max b)

  def depth2[A](tree: FpTree[A]): Int = fold(tree)(_ => 0)((l, r) => 1 + (l max r))

  def map2[A, B](tree: FpTree[A])(op: A => B): FpTree[B] = fold(tree)(a => Leaf(op(a)): FpTree[B])(Branch(_, _))
}
