package com.rtjvm.scala.oop.files

class DirEntry(parentPath: String, name: String) {
  def path: String = parentPath + Directory.SEPARATOR + name

//  def asDirectory: Directory
}
