package scrapbook

object CaseClassVsClass {

  case class Animal(age: Int, name: String)

  class AnimalClass(val age: Int, val name: String)

  object AnimalClass {

    def unapply(animal: AnimalClass): Option[(Int, String)] =
      if (animal.age > 0) Some((animal.age, animal.name)) else None

    def apply(age: Int, name: String): AnimalClass = new AnimalClass(age, name)
  }

  def main(args: Array[String]): Unit = {

    val dog = Animal(3, "greyhound")
    val cat = new AnimalClass(5, "siamese")

    println(dog)
    println(cat)

    val animalClassList: List[AnimalClass] =
      List(AnimalClass(2, "cat"), AnimalClass(3, "dog"), AnimalClass(5, "lion"), AnimalClass(0, "unknown"))

    val animalCaseClassList: List[Animal] = List(Animal(2, "cat"), Animal(3, "dog"), Animal(5, "lion"))

    println(animalCaseClassList.map {
      case Animal(age, name) if age > 10 => s"$name is old"
      case other                         => s"$other"
    })

    println(animalClassList.map {
      case AnimalClass(age, name) => s"This is $name and it's $age years old"
      case other                  => s"$other is not an animal (yet)"
    })
  }
}
