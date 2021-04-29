package gptraining

object ListTest {

  def main(args: Array[String]): Unit = {

    // Basics and list operations
    val n = 10

    val numList = List.range(1, n)
    val twos = List.fill(n)(2)
    val chars = List('s', 'c', 'a', 'l', 'a')
    val strings = List("Flink", "Scala", "Empolis", "Gebr. Pfeiffer")

    println(numList)

    println(numList.filter(_ % 2 == 0))

    println(numList.tail.takeWhile(_ % 2 == 0))

    println(strings.mkString("_"))

  }
}
