package fpinscala.codesnippets

import fpinscala.supportfiles._
import fpinscala.supportfiles.FpList._

object ListTest1 {

  def main(args: Array[String]): Unit = {

    val l1 = FpList(1, 2, 1, 4)
    val l2 = FpList(1, 4)

    println(hasSubsequence(l1, l2))

  }
}
