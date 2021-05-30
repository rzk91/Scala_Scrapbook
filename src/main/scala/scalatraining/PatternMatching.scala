package scalatraining

import scalatraining.objects.PersonScala

object PatternMatching {

  def main(args: Array[String]): Unit = {

    // Pattern matching
    val personList = List(PersonScala("Max", 20), PersonScala("Anna", 25), PersonScala("Thomas", 30), Nil)

    personList
      .map {
        case PersonScala(name, age: Int) if age > 22 => name
        case _                                       => "..."
      }
      .foreach(println)
  }

  def square(x: Int): Int = x * x
}
