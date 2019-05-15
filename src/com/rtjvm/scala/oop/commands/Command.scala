package com.rtjvm.scala.oop.commands

import com.rtjvm.scala.oop.filesystem.State

trait Command {
  // Abstract method
  def apply(state: State): State
}

object Command {
  val MKDIR = "mkdir"

  def emptyCommand: Command = new Command {
    override def apply(state: State): State = state
  }

  // Anonymous Class
  def incompleteCommand(name: String): Command = new Command {
    def apply(state: State): State = state.setMessage(name + ": incomplete command" )
  }

  def from(input: String): Command = {
    val tokens: Array[String] = input.split(" ")
    if (tokens.isEmpty) emptyCommand
    else if(MKDIR.equals(tokens(0))){

      if(tokens.length < 2) incompleteCommand(tokens(0))
      else new Mkdir(tokens(1))

    }else new UnknownCommand
  }
}
