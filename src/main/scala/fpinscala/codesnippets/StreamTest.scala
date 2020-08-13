package fpinscala.codesnippets

import fpinscala.supportfiles.FpStream
import fpinscala.supportfiles.FpStream.empty

object StreamTest {

  def main(args: Array[String]): Unit = {

    val s = FpStream(1 to 10: _*)
    val s2 = FpStream(1, 2, 3, 4, 5)

    println(s2.scanRight(0)(_ + _).toList)
  }
}
