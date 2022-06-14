package scrapbook

import java.io.File

import scala.annotation.tailrec

object TestParser {

  val path: String =
    "C:\\Users\\werm007\\Documents\\Work\\Customers\\Pfeiffer\\New_Data\\test"

  def main(args: Array[String]): Unit =
    getFileList(path).foreach(println)

  private def getListOfFiles(path: String): IndexedSeq[String] = {
    val listDir = new File(path).listFiles.toIndexedSeq
    listDir.withFilter(isCsvFile).map(_.getAbsolutePath) ++
    listDir
      .withFilter(_.isDirectory)
      .flatMap(d => getListOfFiles(d.getAbsolutePath))
  }

  private def getListOfFiles2(path: String): IndexedSeq[String] = {
    val file = new File(path)
    getFileStream(file).withFilter(isCsvFile).map(_.getAbsolutePath).toIndexedSeq
  }

  private def getFileStream(file: File): Stream[File] =
    file #:: (if (file.isDirectory) {
                Option(file.listFiles).toStream.flatten.flatMap(getFileStream)
              } else {
                Stream.empty
              })

  private def getFileList(path: String): List[String] = {
    @tailrec
    def loop(unchecked: List[File], results: List[File]): List[File] = unchecked match {
      case h :: t =>
        val fileList = h.listFiles.toList
        val dirs = fileList.filter(_.isDirectory)
        val files = fileList.filter(isCsvFile)
        loop(t ++ dirs, if (files.isEmpty) results else results ++ files)
      case _ => results
    }
    loop(new File(path) :: Nil, Nil).map(_.getAbsolutePath)
  }

  private def getDirectoryList(path: String): List[String] = {
    @tailrec
    def loop(unchecked: List[File], results: List[File]): List[File] = unchecked match {
      case h :: t =>
        val fileList = h.listFiles.toList
        val dirs = fileList.filter(_.isDirectory)
        loop(t ++ dirs, if (fileList.lengthCompare(dirs.length) == 0) results else h :: results)
      case _ => results
    }
    loop(new File(path) :: Nil, Nil).withFilter(_.listFiles.forall(isCsvFile)).map(_.getPath)
  }

  private def isCsvFile(file: File): Boolean = file.isFile && file.getName.endsWith(".csv")
}
