package com.rtjvm.scala.oop.commands
import com.rtjvm.scala.oop.files.Directory
import com.rtjvm.scala.oop.filesystem.State

class Mkdir(name: String) extends Command {
  override def apply(state: State): State = {
    val wd = state.wd
    if(wd.hasEntry(name)) state.setMessage(name + ": already has entry")
    else if(name.contains(Directory.SEPARATOR)) state.setMessage(name + " must not contains separators")
    else if(checkIllegal(name)) state.setMessage(name + ": illegal entry name")
    else doMkDir(state, name)
  }

  def checkIllegal(name: String) = name.contains(".")

  def doMkDir(state: State, name: String): State = state.setMessage("hhh")
}
