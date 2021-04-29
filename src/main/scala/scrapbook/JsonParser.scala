package scrapbook

import com.typesafe.scalalogging.LazyLogging
import scrapbook.util.JsonUtil

import scala.collection.immutable.ListMap

object JsonParser extends LazyLogging {

  private val valueOrder: List[String] =
    List("id", "label", "value", "unit", "tooltip", "required", "onlineUpdatable", "range", "type", "list")

  private val keyOrder1: List[String] =
    List(
      "startup_messages",
      "ignore_time_in_seconds",
      "finding_messages_bosch1",
      "finding_messages_compax1",
      "findings_for_case1",
      "days_for_case1",
      "case_severity1",
      "days_for_reset1",
      "email_address_case_owners1"
    )

  private val keyOrder2: List[String] =
    List(
      "finding_messages_bosch2",
      "finding_messages_compax2",
      "findings_for_case2",
      "days_for_case2",
      "case_severity2",
      "days_for_reset2",
      "email_address_case_owners2"
    )

  def main(args: Array[String]): Unit = {
    val jsonLocation = args.head

    val currentConfig = ListMap(
      (keyOrder1 ++ keyOrder2).zip((keyOrder1 ++ keyOrder2).map(JsonUtil.readJsonFile(jsonLocation).head)): _*
    )

    val modifiedConfig = currentConfig.map {
      case (str, vMap: Map[String, Any]) =>
        val newValueOrder = valueOrder.filter(vMap.contains)
        str -> ListMap(newValueOrder.zip(newValueOrder.map(vMap)): _*)
    }

    val incompleteConfig = modifiedConfig.filter { case (k, _) => k.endsWith("2") }

    val expandedConfig = (3 to 10).flatMap { n =>
      incompleteConfig.map {
        case (str, v: Map[String, Any]) =>
          str.replace("2", s"$n") -> v
            .transform {
              case (inner_str, inner_v) =>
                if (inner_str == "id" || inner_str == "label") {
                  inner_v.toString.replace("2", s"$n")
                } else {
                  inner_v
                }
            }
      }
    }

    val finalConfig = ListMap(modifiedConfig.toIndexedSeq ++ expandedConfig: _*)

    val finalJson = JsonUtil.convertToJsonString(finalConfig)

    reflect.io.File("bns03blob_new.json").writeAll(finalJson)

  }
}
