package scrapbook

trait ExtendedBasicTrait extends BasicTrait {

  def extendedBasicMethod: String = {
    basicVar += 1
    "Extended " + super.basicMethod
  }
}
