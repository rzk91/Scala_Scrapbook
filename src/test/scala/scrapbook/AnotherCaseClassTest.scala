package scrapbook

import org.scalatest.funsuite.AnyFunSuiteLike
import org.scalatest.matchers.should.Matchers
import scrapbook.util.BasicTestSpec

class AnotherCaseClassTest extends BasicTestSpec with AnyFunSuiteLike with Matchers {

  override protected def beforeEachTest(): Unit =
    logger.error("Now running `beforeEach` inside main test class...")

  override protected def afterEachTest(): Unit =
    logger.error("Now running `afterEach` inside main test class...")

  test("A simple test") {
    1 should be(1)
  }

  test("A slightly complicated test") {
    val a: Option[Int] = None
    val b: Option[Int] = Some(2)

    multiply(a, b) should be(200)
  }
}
