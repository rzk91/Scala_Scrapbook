package fpinscala.codesnippets

import fpinscala.supportfiles.{Branch, Leaf}
import fpinscala.supportfiles.FpTree._

object TreeTest {
  def main(args: Array[String]): Unit = {
    val t = Branch(Branch(Leaf(10), Branch(Leaf(4), Leaf(8))), Leaf(20))

    println(size(t))
    println(size2(t))
  }
}
