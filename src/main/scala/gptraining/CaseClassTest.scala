package gptraining

object CaseClassTest {

  def main(args: Array[String]): Unit = {

    // Case classes
    val personJava = new PersonJava("Max", 20)
    val personScala = PersonScala2("Max", 20)

    println(personJava)
    println(personScala)

    val person2Scala = personScala.copy("Alice")

    println(personScala.senior)

  }
}
