package scalatraining.objects

case class PersonScala2(name: String, age: Int, weight: Option[Double] = None) {
  def senior: Boolean = age > 60
}
