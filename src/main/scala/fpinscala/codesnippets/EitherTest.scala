package fpinscala.codesnippets

import fpinscala.supportfiles.{FpEither, FpLeft, FpRight}

object EitherTest {

  def main(args: Array[String]): Unit = {

    case class Person(name: String, age: Int)
    sealed class Name(value: String)
    sealed class Age(value: Int)

    def mkName(name: String): FpEither[String, String] = if (name == "" || name == null) FpLeft("Name is empty") else FpRight(name)

    def mkAge(age: Int): FpEither[String, Int] = if (age < 0) FpLeft("Invalid age") else FpRight(age)

    def mkPerson(name: String, age: Int): FpEither[String, Person] = mkName(name).map2(mkAge(age))(Person)

    println(mkPerson("", -1))
  }

}
