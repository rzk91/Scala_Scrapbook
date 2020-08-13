package gptraining

object OptionTest {

  def main(args: Array[String]): Unit = {

    // Option
    val emptyList = List.empty[Int]
    val numList = List.range(1, 10)

//    if (mean(emptyList).isDefined) println("Mean exists") else println("Empty list")

    println(mean(numList))
  }

  private def mean(l: List[Int]): Any = if (l.isEmpty) "Empty list" else l.sum/l.length.toDouble

  private def mean_1(l: List[Int]): Double = l.sum/l.length.toDouble

  private def meanOutput(x: Option[Double]): String = x match {
    case Some(value) => value.toString
    case None => "Empty sequence"
  }

}
