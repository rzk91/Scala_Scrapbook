package scrapbook

import scala.language.postfixOps
import scala.sys.process._
import scala.util.Try
import scala.collection.JavaConverters._

object PythonProcess {

  def main(args: Array[String]): Unit = {
    println(Try("python3 --version".!!).toOption)

    val v = Seq(
      "python",
      "-c",
      "import pandas as pd;" +
      "print(pd.Timestamp(1000, unit='M'))"
    ).!!

    println(v)
  }
}
