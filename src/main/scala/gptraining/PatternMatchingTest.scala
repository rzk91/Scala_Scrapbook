package gptraining

object PatternMatchingTest {

  def main(args: Array[String]): Unit = {

    // Pattern matching
    val personList = List(PersonScala("Max", 20), PersonScala("Anna", 25), PersonScala("Thomas", 30), Nil)

    personList.collect {
      case PersonScala(name, age: Int) if age > 22 => name
    }.foreach(println)
  }
}
