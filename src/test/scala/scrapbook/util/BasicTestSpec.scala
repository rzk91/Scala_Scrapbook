package scrapbook.util

import org.scalatest.{BeforeAndAfterEach, Suite}

trait BasicTestSpec extends BareBonesTestSpec with Suite with BeforeAndAfterEach with TestHelper {

  protected def beforeEachTest(): Unit = {}
  protected def afterEachTest(): Unit = {}

  final override protected def beforeEach(): Unit = {
    beforeEachTest()
    logger.warn("Now running `beforeEach` inside BasicTestSpec...")
    super.beforeEach()
  }

  final override protected def afterEach(): Unit = {
    super.afterEach()
    logger.warn("Now running `afterEach` inside BasicTestSpec...")
    afterEachTest()
  }
}
