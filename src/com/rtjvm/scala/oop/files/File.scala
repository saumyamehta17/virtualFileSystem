package com.rtjvm.scala.oop.files

import java.nio.file.FileSystemException

class File(override val parentPath: String, override val name: String, val contents: String) extends DirEntry(parentPath, name) {
  def asDirectory: Directory = throw new FileSystemException(" Can not converted to Directory.")
  def asFile: File = this
  def getType = "File"
  def isDirectory: Boolean = false
  def isFile: Boolean = true

  def appendContents(contents: String): File = {
    val newContent = this.contents + "\n" + contents
    setContents(newContent)
  }
  def setContents(contents: String): File = {
    new File(parentPath, name, contents)
  }

}

object File{

  def empty(parentPath: String, name: String): File =
    new File(parentPath, name, contents = "")
}
