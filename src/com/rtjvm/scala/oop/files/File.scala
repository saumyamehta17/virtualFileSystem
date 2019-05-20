package com.rtjvm.scala.oop.files

import java.nio.file.FileSystemException

class File(override val parentPath: String, override val name: String, val contents: String) extends DirEntry(parentPath, name) {
  def asDirectory: Directory = throw new FileSystemException(" Can not converted to Directory.")
  def asFile: File = this
  def getType = "File"
}

object File{

  def empty(parentPath: String, name: String): File =
    new File(parentPath, name, contents = "")
}
