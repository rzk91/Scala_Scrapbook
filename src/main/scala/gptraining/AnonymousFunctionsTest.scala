package gptraining

object AnonymousFunctionsTest {

  def main(args: Array[String]): Unit = {

    // List with case classes
    val personList = List(PersonScala("Max", 21), PersonScala("Anna", 25), PersonScala("Thomas", 30), Nil)

    println(personList.collect {
      case p: PersonScala[Int] if p.age >= 25 => p.age
    })
  }

  def ageMapper[S](p: PersonScala[S]): S = p.age

  def avg: List[Double] => Double = (x: List[Double]) => x.sum / x.length

}
