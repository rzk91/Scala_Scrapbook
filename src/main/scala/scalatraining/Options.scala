package scalatraining

import scala.util.Try

object Options {

  def main(args: Array[String]): Unit = {

    // Option
    val emptyList = List.empty[Int]
    val numList = List.range(1, 10)

    val string: String = "Bla"

    println(Try(string.length).map(_ * 2))

//    println(mean(emptyList).map(_ * 2))
//
//    println(if (mean(emptyList).isDefined) "good" else "bad")
//
//    println(mean(emptyList).getOrElse(0))
//
//    println(mean(numList) match {
//      case Some(v) => v * 2
//      case None    => "..."
//    })

  }

  private def mean_normal(l: List[Int]): Double = if (l.lengthCompare(0) == 0) throw new Exception else l.sum / l.length

  private def mean(l: List[Int]): Option[Double] =
    if (l.lengthCompare(0) == 0) None else Some(l.sum / l.length.toDouble)

}
