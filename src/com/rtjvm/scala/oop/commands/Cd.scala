package com.rtjvm.scala.oop.commands
import com.rtjvm.scala.oop.files.{DirEntry, Directory}
import com.rtjvm.scala.oop.filesystem.State

class Cd(dir: String) extends Command {
  /*
    cd /something/somethingelse/.../
    cd a/b/c

  */
  override def apply(state: State): State = {
    // 1. find Root
    val root = state.root
    val wd = state.wd

    // 2. find absolute path of directory I want cd to
    val absolutePath =
      if(dir.startsWith(Directory.SEPARATOR)) dir
      else wd + Directory.SEPARATOR + dir

    // 3. find the directory to cd to, given path
    val destinationEntry = doFindEntry(root, absolutePath)

    // 4. change the state given the new directory
    if(destinationEntry == null || !destinationEntry.isDirectory)
      state.setMessage(dir + ": No directory found")
    else State(root, destinationEntry.asDirectory)
  }

  def doFindEntry(root: DirEntry, path: String): DirEntry = ???
}
