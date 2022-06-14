package scrapbook

trait IntermediateTrait extends BasicTrait {

  def intermediateMethod: String = {
    basicVar += 1
    "Intermediate " + super.basicMethod
  }
}
