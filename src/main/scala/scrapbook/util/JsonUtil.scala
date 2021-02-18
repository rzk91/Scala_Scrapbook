package scrapbook.util

import java.io.InputStream

import com.fasterxml.jackson.databind.{ObjectMapper, ObjectWriter}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.typesafe.scalalogging.LazyLogging

import scala.io.{BufferedSource, Source}

object JsonUtil extends Serializable with LazyLogging {

  @transient lazy val mapper: ObjectMapper = {
    val mapper = new ObjectMapper()
    mapper.registerModule(DefaultScalaModule)
    mapper
  }

  @transient lazy val configWriter: ObjectWriter = mapper.writerFor(classOf[Map[String, Any]])

  def convertToJsonString[T](value: T): String = {
    val writer = new java.io.StringWriter()
    mapper.writeValue(writer, value)
    writer.toString
  }

  def readJsonFile(filename: String): List[Map[String, Any]] = readFile(Source.fromFile(filename))

  def readJsonFile(file: InputStream): List[Map[String, Any]] =
    readFile(Source.fromInputStream(file))

  def readJson(json: String): Map[String, Any] =
    mapper.readValue[Map[String, Any]](json, classOf[Map[String, Any]])

  private def readFile(buffer: BufferedSource): List[Map[String, Any]] =
    try {
      buffer.getLines.toList.map(readJson)
    } catch {
      case e: Exception =>
        logger.debug(s"Error parsing message texts file: $e")
        List.empty
    } finally {
      buffer.close()
    }

  /**
    * Converts a map to a json-string.
    * Json is sorted by keys, all whitespaces are removed
    *
    * @param config config
    * @return
    */
  def configToJsonStr(config: Map[String, Any]): String = {
    val sorted = scala.collection.immutable.ListMap(config.toSeq.sortBy(_._1): _*)
    configWriter.writeValueAsString(sorted).replace(" ", "")
  }
}
