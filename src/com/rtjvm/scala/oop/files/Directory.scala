package com.rtjvm.scala.oop.files

class Directory(val parentPath: String, val name: String, contents: List[DirEntry]) extends DirEntry(parentPath, name) {
  def hasEntry(name: String): Boolean = false

  def getAllFoldersInPath: List[String] = path.substring(1).split(Directory.SEPARATOR).toList
  // /a/b/c => List['a','b','c']

  def findDescendent(path: List[String]): DirEntry = ???

  def addEntry(newEntry: DirEntry): Directory = ???

  def findEntry(entryName: String): Directory = ???

  def replaceEntry(entryName: String, directory: Directory): Directory = ???

}

object Directory {
  val SEPARATOR = "/"
  val ROOT_PATH = "/"

  def ROOT: Directory = Directory.empty("", "")
  def empty(parentPath: String, name: String) = {
    new Directory(parentPath, name, List())
  }
}
