package com.rtjvm.scala.oop.commands

import com.rtjvm.scala.oop.files.Directory
import com.rtjvm.scala.oop.filesystem.State

class Mkdir(name: String) extends CreateEntry(name) {
  def doCreateSpecificEntry(state: State): Directory = Directory.empty(state.wd.path, name)
}
