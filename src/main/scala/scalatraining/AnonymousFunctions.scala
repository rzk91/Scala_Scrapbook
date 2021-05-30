package scalatraining

import scalatraining.objects.{PersonScala, PersonScala2}

object AnonymousFunctions {

  def main(args: Array[String]): Unit = {

    // List with case classes
    val personList = List(PersonScala2("Max", 21), PersonScala2("Anna", 25), PersonScala2("Thomas", 30), Nil)

    println(personList.collect {
      case p: PersonScala2 if p.age >= 25 => p.age
    })
  }

  def ageMapper[S](p: PersonScala[S]): S = p.age

  def avg: List[Double] => Double = (x: List[Double]) => x.sum / x.length

}
