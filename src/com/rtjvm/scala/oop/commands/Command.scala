package com.rtjvm.scala.oop.commands

import com.rtjvm.scala.oop.files.DirEntry
import com.rtjvm.scala.oop.filesystem.State

trait Command {
  // Abstract method
  def apply(state: State): State = {
    val pwdString: String = getPwdString(state.wd)
    state.setMessage(pwdString)
  }

  def getPwdString(dir: DirEntry):String = dir.path

}

object Command {
  val MKDIR = "mkdir"
  val LS = "ls"
  val PWD = "pwd"
  val TOUCH = "touch"
  val CD = "cd"
  val RM = "rm"
  val ECHO = "echo"

  def emptyCommand: Command = new Command {
    override def apply(state: State): State = state
  }

  // Anonymous Class
  def incompleteCommand(name: String): Command = new Command {
    override def apply(state: State): State = state.setMessage(name + ": incomplete command" )
  }

  def from(input: String): Command = {
    val tokens: Array[String] = input.split(" ")
    if (tokens.isEmpty) emptyCommand
    else if(MKDIR.equals(tokens(0))) {

      if (tokens.length < 2) incompleteCommand(tokens(0))
      else new Mkdir(tokens(1))
    }
    else if(LS.equals(tokens(0))) {
      new Ls
    }
    else if(PWD.equals(tokens(0))){
      new Pwd
    }else if(TOUCH.equals(tokens(0))){
      if (tokens.length < 2) incompleteCommand(tokens(0))
      else new Touch(tokens(1))
    }else if(CD.equals(tokens(0))){
      if (tokens.length < 2) incompleteCommand(tokens(0))
      else new Cd(tokens(1))
    }else if(RM.equals(tokens(0))){
      if (tokens.length < 2) incompleteCommand(tokens(0))
      else new Rm(tokens(1))
    }else if(ECHO.equals(tokens(0))){
      if (tokens.length < 2) incompleteCommand(tokens(0))
      else new Echo(tokens.tail)
    }else new UnknownCommand
  }

}
