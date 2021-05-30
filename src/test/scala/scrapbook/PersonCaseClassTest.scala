package scrapbook

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.BeforeAndAfter

class PersonCaseClassTest extends AnyFlatSpec with BeforeAndAfter with Matchers {

  "a simple test" should "just work properly" in {
    1 shouldBe 1
  }
}
