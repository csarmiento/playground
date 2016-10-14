package co.runtime.scala.helpers

import java.io.File
import scala.io.Source

object GitHelper {
  val fileSep = File.separator


  private def getChanges(filePath: String): List[String] = {
    Source.fromFile(filePath).getLines().toList
  }

  def copyToTarget(changedFiles: List[String], targetDir: String) = {
    changedFiles.map(path => new File(path)).foreach(f => println("cp " + f.getAbsolutePath + " " + targetDir))
  }

  def copyBack(changedFiles: List[String], targetDir: String) = {
    changedFiles.map(path => path.splitAt(path.lastIndexOf(fileSep))).foreach(t => println("cp " + targetDir + t._2 + " " + t._1))
  }

  def main(args: Array[String]) {
    if (args.size == 2) {
      val changedFiles = getChanges(args(0))
      val targetDir = args(1)
      copyToTarget(changedFiles, targetDir)
      println()
      copyBack(changedFiles, targetDir)
    } else {
      println("Usage: scala GitHelper <Changes Input File> <Target Directory>")
    }
  }
}
