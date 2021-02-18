package scrapbook

import com.typesafe.scalalogging.LazyLogging
import scrapbook.util.JsonUtil

import scala.collection.immutable.ListMap

object JsonParser extends LazyLogging {

  def main(args: Array[String]): Unit = {
    val jsonLocation = args.head

    val currentConfig = JsonUtil.readJsonFile(jsonLocation).head
    val incompleteConfig = currentConfig.filter { case (k, _) => k.endsWith("2") }

    val expandedConfig = (3 to 100).flatMap { n =>
      incompleteConfig.map {
        case (str, v) => str.replace("2", s"$n") -> v
      }
    }

    val finalConfig = ListMap(currentConfig.toIndexedSeq ++ expandedConfig: _*)

    val finalJson = JsonUtil.convertToJsonString(finalConfig)

    reflect.io.File("rieter-uc001-new.json").writeAll(finalJson)

  }
}
