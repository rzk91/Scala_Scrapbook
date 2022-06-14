package fpinscala.codesnippets

import fpinscala.TimeHelper._
import fpinscala.supportfiles.FpOption._
import fpinscala.supportfiles.FpSome

object OptionTest {

  def main(args: Array[String]): Unit = {
    val x = List(FpSome(10), FpSome(20), FpSome(30))
    val y: List[Double] = List(1, 2, 3, 4, 5)

    println(timeIt(sequence(x)))
    println(timeIt(sequence2(x)))
  }
}
