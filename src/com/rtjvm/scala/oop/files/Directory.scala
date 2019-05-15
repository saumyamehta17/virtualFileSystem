package com.rtjvm.scala.oop.files

class Directory(val parentPath: String, val name: String, contents: List[DirEntry]) extends DirEntry(parentPath, name) {
  def hasEntry(name: String): Boolean = false
}

object Directory {
  val SEPARATOR = "/"
  val ROOT_PATH = "/"

  def ROOT: Directory = Directory.empty("", "")
  def empty(parentPath: String, name: String) = {
    new Directory(parentPath, name, List())
  }
}
