package com.rtjvm.scala.oop.files

abstract class DirEntry(val parentPath: String, val name: String) {
  def path: String = {
    val separatorNeeded =
      if(name.isEmpty) Directory.SEPARATOR
      else ""
    parentPath + separatorNeeded + name
  }

  def asDirectory: Directory
  def asFile: File

  def getType: String

  def isDirectory: Boolean
  def isFile: Boolean
}
