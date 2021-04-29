package scrapbook.util

import com.typesafe.scalalogging.LazyLogging
import org.apache.poi.ss.usermodel.{Row, WorkbookFactory}

import java.io.File
import scala.collection.JavaConverters.asScalaIteratorConverter
import scala.reflect.io.{File => ScalaFile}
import scala.util.{Failure, Success, Try}

object ExcelParser extends LazyLogging {

  def main(args: Array[String]): Unit = {
    val filePath = args.headOption.map(_.replaceAll("\"", ""))

    logger.info(s"Input file: $filePath")

    val parsedFile = filePath.flatMap(parseExcelFile)

    logger.warn(s"List of maintenance codes that are not in Excel file: ${existingIDs
      .filterNot(id => parsedFile.fold(false)(_.map(_.id).contains(id)))}")
    parsedFile.map(_.map(convertRowToCsvLine)).foreach(ScalaFile("html_maintenance_codes.csv").writeAll(_: _*))
  }

  case class ExcelRow(id: String, text: String, url: String)

  private def parseExcelFile(filePath: String): Option[IndexedSeq[ExcelRow]] = {
    val workbook = WorkbookFactory.create(new File(filePath))

    val output = Try {
      val currentSheet = workbook.getSheetAt(0)
      val header = {
        val headerRow = currentSheet.getRow(0)
        (0 until headerRow.getPhysicalNumberOfCells).map { i =>
          headerRow.getCell(i, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK).getStringCellValue
        }
      }
      logger.info(s"$header")
      (1 until currentSheet.getLastRowNum).map { idx =>
        val row = currentSheet.getRow(idx)
        logger.trace(s"${row.cellIterator.asScala.map(_.getStringCellValue).toList}")
        ExcelRow(
          row.getCell(1, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK).getStringCellValue,
          row.getCell(0, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK).getStringCellValue,
          row.getCell(2, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK).getStringCellValue
        )
      }
    }
    workbook.close()

    output match {
      case Success(value) => Some(value)
      case Failure(e) =>
        logger.error(s"Exception while parsing workbook", e)
        None
    }
  }

  private def convertRowToCsvLine(row: ExcelRow): String =
    "\"" +
    s"${row.id}" +
    "\",\"" +
    s"${row.text}" +
    "\",\"\",\"\",\"true\",\"" +
    s"${row.url}" +
    "\"\n"

  private val existingIDs: List[String] = List(
    "ID_c8db5b7100e805790a01084c5078bba8-e2fcdc6900e805790a01084c1e9e44d1",
    "ID_156ff1e80115cc530a01084c15382240-b033117e0115cc530a01084c462dd545",
    "ID_53926ff1515f68ad0a01084c7df05c2c-4209480f515f68ad0a01084c62ee3c72",
    "ID_c8db5b7100e805790a01084c5078bba8-e2fcdc6900e805790a01084c1e9e44d1",
    "ID_c47a4fbac11ebab90a01084c74f7c53b-b911d368c11ebab90a01084c03fdca9d",
    "ID_8e1fddb367b595170a14066973eed5d5-8814bdb667b5a89c0a01084c435b7c0a",
    "ID_563d7fdf204eb20c0a01084c5ac3e522-6f15be8b204eb20c0a01084c057a09a6",
    "ID_86fc27c5fe829ddd0a01084c520f07da-af1e866dfe829ddd0a01084c26f3cca0",
    "ID_3d82608831e640cd0a01084c7536bd82-7d8a20e231e640cd0a01084c1f1f60a3",
    "ID_defd62f7205f32150a01084c6483253b-3f19867e205f32150a01084c51d02e3e",
    "ID_93ebd49d00fe00e60a01084c015f5449-492dfbc300fe00e60a01084c62003ef5",
    "ID_db3e1a9100efb8120a01084c7bb65d5d-612922e000efb8120a01084c690363a0",
    "ID_1f44aa610142a9680a01084c4279e1e0-d3d8820c0142a9680a01084c0b3cec2f",
    "ID_329010e4975e32350a01084c731f6266-7cbc14d4975e32350a01084c2b15d3fc",
    "ID_89b21cc625e9395e0a01084c4de48a7e-3b79808625e9395e0a01084c3d63431d",
    "ID_dddf3d25012a41db0a01084c207599ff-6522fc17012a41db0a01084c6704b110",
    "ID_1fb7429d021ef0f60a01084c733bb906-3aeec9be021ef0f60a01084c31200397",
    "ID_93ebd49d00fe00e60a01084c015f5449-492dfbc300fe00e60a01084c62003ef5",
    "ID_67f502fe256376200a01084c2c7eda4f-c4482c95256376200a01084c2120b1df",
    "ID_ae4c003325f0ad890a01084c31a426c4-d5083d2e25f0ad890a01084c2555d813",
    "ID_f9f6982c37c275aa0a01084c1ec4e32a-58dddad637c275aa0a01084c555d0ce9",
    "ID_db3e1a9100efb8120a01084c7bb65d5d-612922e000efb8120a01084c690363a0",
    "ID_7af7952157dd69920a140669729c3373-f78b309357dde39a0a01084c7058a67a",
    "ID_b5cea367c115967e0a01084c653bcc97-f255cc71c115967e0a01084c4f705ad0",
    "ID_6a80f6d6fd309f1c0a140679406b5725-aeeb3e1dfd350b110a01084c4ea40c33",
    "ID_b1397256c21d6a7b0a01084c36ecb31e-c4a5b12bc21d6a7b0a01084c10e1555f",
    "ID_233a0fa401337fa90a01084c3eee73e5-9f47f23c01337fa90a01084c6f94e8d5"
  )
}
