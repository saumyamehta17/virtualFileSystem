package com.rtjvm.scala.oop.commands

import com.rtjvm.scala.oop.files.{Directory, File}
import com.rtjvm.scala.oop.filesystem.State

class Touch(name: String) extends CreateEntry(name) {
  def doCreateSpecificEntry(state: State): File = File.empty(state.wd.path, name)
}
