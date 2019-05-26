package com.rtjvm.scala.oop.commands
import com.rtjvm.scala.oop.files.Directory
import com.rtjvm.scala.oop.filesystem.State

class Rm(name: String) extends Command {
  override def apply(state: State): State = {
    // 1. get working dir
    val wd = state.wd

    // 2. get absolute path
    val absolutePath =
      if(name.startsWith(Directory.ROOT_PATH)) name
      else if(wd.path.equals(Directory.ROOT_PATH)) wd.path + name
      else wd.path + Directory.SEPARATOR + name

    // 3. do some checks
    if(absolutePath.equals(Directory.ROOT_PATH))
      state.setMessage("unpermitted action")
    else
      doRm(state, absolutePath)
  }

  def doRm(state: State, absolutePath: String): State ={

    def doRmHelper(currDir: Directory, path: List[String]): Directory = {

      if(path.isEmpty) currDir
      else if(path.tail.isEmpty) currDir.removeEntry(path.head)
      else {
        val nextDir = currDir.findEntry(path.head)
        if (nextDir.isDirectory) {
          val nextNewDir = doRmHelper(nextDir.asDirectory, path.tail)
          if (nextNewDir == currDir) currDir
          else currDir.replaceEntry(currDir.name, nextNewDir)
        }else currDir
      }
    }


    val tokens = absolutePath.substring(1).split(Directory.SEPARATOR).toList
    val newRoot = doRmHelper(state.root, tokens)
    if(newRoot == state.root) state.setMessage("No Directory Found")
    else State(newRoot, newRoot.findDescendent(state.wd.path.substring(1)))

    // 4. find the entry to remove


    // 5. update structure like mkdir
  }
}
