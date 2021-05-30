package scalatraining.objects

case class PersonScala2(name: String, age: Int) {
  def senior: Boolean = age > 60
}
