package com.rtjvm.scala.oop.commands
import com.rtjvm.scala.oop.files.{DirEntry, Directory}
import com.rtjvm.scala.oop.filesystem.State

import scala.annotation.tailrec

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
      else if(wd.isRoot) wd.path + dir
      else wd.path + Directory.SEPARATOR + dir

    // 3. find the directory to cd to, given path
    val destinationEntry = doFindEntry(root, absolutePath)

    // 4. change the state given the new directory
    if(destinationEntry == null || !destinationEntry.isDirectory)
      state.setMessage(dir + ": No directory found")
    else State(root, destinationEntry.asDirectory)
  }

  def doFindEntry(root: Directory, path: String): DirEntry = {
    @tailrec
    def findEntryHelper(currDirectory: Directory, path: List[String]): DirEntry = {
      if(path.isEmpty || path.head.isEmpty) currDirectory
      else if(path.tail.isEmpty) {
        println("path head in findEntryHelper " + path.head)
        currDirectory.findEntry(path.head)
      }
      else{
        val nextDir = currDirectory.findEntry(path.head)
        if (nextDir == null) null
        else findEntryHelper(nextDir.asDirectory, path.tail)
      }
    }


    // 1. tokens
    val tokens: List[String] = path.substring(1).split(Directory.SEPARATOR).toList
    //println("absolute path " + tokens.toString())

    // 1.5 eliminating / collapse relative tokens
      /*
       /a/. => ["a", ".", "."] => ["a"]
       /a/./. => ["a", ".", "."] => ["a"]
       /a/..  => ["a", ".."] => []
       /a/b/.. => ["a", "b", ".."] => ["a"]

       List init - will give list excluding last element
     */
    def collapseRelativeTokens(path: List[String], result: List[String] = List()): List[String] = {
        if(path.isEmpty) result
        else if(".".equals(path.head)) collapseRelativeTokens(path.tail, result)
        else if("..".equals(path.head)) {
          if (result.isEmpty) null
          else collapseRelativeTokens(path.tail, result.init)
        }else collapseRelativeTokens(path.tail, result :+ path.head)
      }

    // 2.
    val newTokens = collapseRelativeTokens(tokens)

    if (newTokens == null) null
    else findEntryHelper(root, newTokens)
  }
}
