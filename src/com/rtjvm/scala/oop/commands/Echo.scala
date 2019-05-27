package com.rtjvm.scala.oop.commands
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

  def doEcho(state: State, contents: String, filename: String, append: Boolean): State = ???

  def createContent(args: Array[String], topIndex: Int): String = {
    def createContentHelper(currIndex: Int = 0, accumalator: String = ""): String = {
      if(currIndex >= topIndex) accumalator
      else createContentHelper(currIndex+1, accumalator + " " + args(currIndex))
    }
    createContentHelper()
  }


}
