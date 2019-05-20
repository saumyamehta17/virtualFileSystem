package com.rtjvm.scala.oop.files

import java.nio.file.FileSystemException

import scala.annotation.tailrec

class Directory(override val parentPath: String, override val name: String, val contents: List[DirEntry]) extends DirEntry(parentPath, name) {
  def hasEntry(name: String): Boolean = findEntry(name) != null
  def getType = "Directory"
  def asDirectory: Directory = this
  def asFile = throw new FileSystemException("Can not convert into File")

  def getAllFoldersInPath: List[String] = path.substring(1).split(Directory.SEPARATOR).toList.filter(e => !e.isEmpty)
  // /a/b/c => List['a','b','c']

  def findDescendent(path: List[String]): Directory =
    if(path.isEmpty) this
    else findEntry(path.head).asDirectory.findDescendent(path.tail)

  def addEntry(newEntry: DirEntry): Directory =
    new Directory(parentPath, name, contents :+ newEntry)

  def findEntry(entryName: String): DirEntry = {
    def findEntryHelper(name: String, contents: List[DirEntry]): DirEntry ={
      if(contents.isEmpty) null
      else if(contents.head.name.equals(name)) contents.head
      else findEntryHelper(name, contents.tail)
    }
    findEntryHelper(entryName, contents)
  }

  def replaceEntry(entryName: String, newEntry: DirEntry): Directory =
    new Directory(parentPath, name, contents.filter(e => !e.name.equals(entryName)) :+ newEntry)

}

object Directory {
  val SEPARATOR = "/"
  val ROOT_PATH = "/"

  def ROOT: Directory = Directory.empty("", "")
  def empty(parentPath: String, name: String) = {
    new Directory(parentPath, name, List())
  }
}
