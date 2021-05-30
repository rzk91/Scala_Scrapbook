package scrapbook.util

import com.typesafe.scalalogging.LazyLogging
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec

trait BasicTestSpec extends AnyFlatSpec with BeforeAndAfterEach with LazyLogging {

  protected def beforeEachTest(): Unit = {}
  protected def afterEachTest(): Unit = {}

  final override protected def beforeEach(): Unit = {
    beforeEachTest()
    logger.warn("Now running `beforeEach` inside BasicTestSpec...")
    super.beforeEach
  }

  final override protected def afterEach(): Unit = {
    afterEachTest()
    logger.warn("Now running `afterEach` inside BasicTestSpec...")
    super.afterEach()
  }
}
