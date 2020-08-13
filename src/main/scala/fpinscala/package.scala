package object fpinscala {

  case class TimeAndResult[R](time: Double, result: R) {
    override def toString: String = s"Result: $result; time required: $time ms"
  }

  def timeIt[R](block: => R): TimeAndResult[R] = {
    val t0 = System.nanoTime
    val result = block
    val t1 = System.nanoTime
    TimeAndResult((t1 - t0)/1e6, result)
  }

  def timeItLoop[R](n: Int, block: => R): Double = (1 to n).foldLeft(0d)((acc, _) => acc + timeIt(block).time)/n
}
