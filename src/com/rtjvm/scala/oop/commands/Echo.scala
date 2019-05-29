package com.rtjvm.scala.oop.commands
import com.rtjvm.scala.oop.files.{Directory, File}
import com.rtjvm.scala.oop.filesystem.State

class Echo(args: Array[String]) extends Command {

  override def apply(state: State): State = {
    /*
    if no args , state
    if one arg, print to console
    if multiple args
      operator = next to last arg
      if > , add to file(create file if not there)
      if >> , append to file
      else just print to console
  */

    // no args
    if(args.isEmpty) state
    else if(args.length == 1) state.setMessage(args(0))
    else {
      val len = args.length
      val operator = args(len - 2)
      val filename = args(len - 1)
      val contents = createContent(args, len - 2)
      if (operator.equals(">")) doEcho(state, contents, filename, append = false)
      else if (operator.equals(">>")) doEcho(state, contents, filename, append = true)
      else state.setMessage(createContent(args, len))
    }
  }

  def doEchoHelper(currDir: Directory, path: List[String], contents: String, append: Boolean): Directory = {
    /*
    1. if path is empty return currDir
       elseif path tail is empty
        findentry with path head
        if entry is empty - create file
        elseif entry is directory - fail
        else replace entry
       else
        find next Dir to navigate
        call doEchoHelper
        if call fails then - fail
        else replace entry with new Dir
    */

    if(path.isEmpty) currDir
    else if(path.tail.isEmpty){
      val dirEntry = currDir.findEntry(path.head)
      if(dirEntry == null) currDir.addEntry( new File(currDir.path, path.head, contents) )
      else if(dirEntry.isDirectory) currDir
      else {
        if(append) currDir.replaceEntry(path.head, dirEntry.asFile.appendContents(contents))
        else currDir.replaceEntry(path.head, dirEntry.asFile.setContents(contents))
      }
    }else {
      val nextDir = currDir.findEntry(path.head).asDirectory
      val nextNewDir = doEchoHelper(nextDir, path.tail, contents, append)
      if(nextNewDir == nextDir) nextDir
      else nextDir.replaceEntry(path.head, nextNewDir)
    }
  }
  def doEcho(state: State, contents: String, filename: String, append: Boolean): State = {
    if(filename.contains(Directory.SEPARATOR)) state.setMessage("Echo: filename must not contains separator")
    else{
      val newRoot: Directory = doEchoHelper(state.root, state.wd.getAllFoldersInPath :+ filename, contents, append)
      if(newRoot == state.root) state.setMessage(filename + ": No Such File")
      else State(newRoot, newRoot.findDescendent(state.wd.getAllFoldersInPath))
    }
  }

  def createContent(args: Array[String], topIndex: Int): String = {
    def createContentHelper(currIndex: Int = 0, accumalator: String = ""): String = {
      if(currIndex >= topIndex) accumalator
      else createContentHelper(currIndex+1, accumalator + " " + args(currIndex))
    }
    createContentHelper()
  }


}
