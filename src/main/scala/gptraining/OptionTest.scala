package gptraining

object OptionTest {

  def main(args: Array[String]): Unit = {

    // Option
    val emptyList = List.empty[Int]
    val numList = List.range(1, 10)

    println(mean(numList).map(_ * 2))

    println(if (mean(numList).isDefined) "good" else "bad")

    println(mean(emptyList).getOrElse(0))

    println(mean(numList) match {
      case Some(v) => v * 2
      case None    => "..."
    })

  }

  private def mean(l: List[Int]): Option[Double] =
    if (l.lengthCompare(0) == 0) None else Some(l.sum / l.length.toDouble)

}
