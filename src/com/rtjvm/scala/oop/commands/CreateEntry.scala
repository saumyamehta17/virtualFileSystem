package com.rtjvm.scala.oop.commands

import com.rtjvm.scala.oop.files.{DirEntry, Directory}
import com.rtjvm.scala.oop.filesystem.State

abstract class CreateEntry(name: String) extends Command {
  override def apply(state: State): State = {
    val wd = state.wd
    if(wd.hasEntry(name)) state.setMessage(name + ": already has entry")
    else if(name.contains(Directory.SEPARATOR)) state.setMessage(name + " must not contains separators")
    else if(checkIllegal(name)) state.setMessage(name + ": illegal entry name")
    else doCreateEntry(state, name)
  }

  def checkIllegal(name: String) = name.contains(".")

  def doCreateEntry(state: State, name: String): State = {

    def updateStructure(currentDirectory: Directory, path: List[String], newEntry: DirEntry): Directory = {
      if(path.isEmpty) currentDirectory.addEntry(newEntry)
      else{
        //        val oldEntry = path.head
//        println("Bug:  " + currentDirectory.findEntry(path.head))
        val oldEntry = currentDirectory.findEntry(path.head).asDirectory
        currentDirectory.replaceEntry(oldEntry.name, updateStructure(oldEntry, path.tail, newEntry))
      }
      /*
        someDir -> /a -> /b -> new /c
        new someDir -> a -> b -> c
      */
      /*
        nested
        /a/b
          /c
          /d
          new e

       => new a
            new b
              /c
              /d
              new e
      */
    }


    val wd = state.wd

    // 1. all the directories in the full path
    val allDirsInPath = wd.getAllFoldersInPath

    // 2. create new directory entry in the wd
//    val newDir = Directory.empty(wd.path, name)
    val newEntry = doCreateSpecificEntry(state)
    // 3. update the whole Directory structure starting from root
    // Directory Structure is immutable
    val newRoot = updateStructure(state.root, allDirsInPath, newEntry)

    // 4. find new working directory Instance given wd's full path, in new directory structure
    val newWd = newRoot.findDescendent(allDirsInPath)

    State(newRoot, newWd)
  }

  def doCreateSpecificEntry(state: State): DirEntry
}
