package scrapbook

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scrapbook.util.BasicTestSpec

class PersonCaseClassTest extends AnyFlatSpec with BasicTestSpec with Matchers {

  override protected def beforeEachTest(): Unit =
    logger.error("Now running `beforeEach` inside main test class...")

  override protected def afterEachTest(): Unit =
    logger.error("Now running `afterEach` inside main test class...")

  "a simple test" should "just work properly" in {
    1 should be(1)
  }

  "a slightly complicated test" should "also work properly" in {
    val a: Option[Int] = None
    val b: Option[Int] = Some(2)

    multiply(a, b) should be(200)
  }
}
