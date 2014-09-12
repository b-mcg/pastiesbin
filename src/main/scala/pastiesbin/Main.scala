package pastiesbin

// Imports
import org.rogach.scallop._
import scala.language.reflectiveCalls
import pasties.PasteBinAPI
import scala.util.{Try, Success, Failure}
import pastiesbin.Paste._

/**
  * Author  = b-mcg
  * Email   = b.mc0890@gmail.com
  * License = 
  *
  *   Copyright (C) 2014-2016  b-mcg   <b.mcg0890@gmail.com>
  *
  *   This program is free software: you can redistribute it and/or modify
  *   it under the terms of the GNU General Public License as published by
  *   the Free Software Foundation, either version 3 of the License, or
  *   (at your option) any later version.
  *
  *   This program is distributed in the hope that it will be useful,
  *   but WITHOUT ANY WARRANTY; without even the implied warranty of
  *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  *   GNU General Public License for more details.
  *
  *   You should have received a copy of the GNU General Public License
  *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
  *
  */


/**
  * main
  *
  * Sets up commandline argument parser
  * and attempts to create a new paste.
  *
  */
object Main extends App {

  val Version = "0.0.1"

  // Create command line argument parser
  val opts = new ScallopConf(args) {

    banner("""
            Usage: [--version] [--help] [-l|--lines=<line range>] [-e|--expire=<expire time>]
            [-p|--privacy=<privacy setting>] [-s|--syntax=<code name>] file
           """.stripMargin)

    version(s"pastiesbin $Version")

    // Define command line arguments
    val lines       = opt[List[Int]]("lines", short = 'l', default = Some(List[Int]()), descr = "Lines from file to read and paste", required = false)

    val expireTime  = opt[String]("expire", short = 'e', default = Some("N"), descr = "Sets time for the paste to expire", required = false)

    val privacy     = opt[String]("privacy", short = 'p', default = Some("1"), descr = "Sets privacy setting of paste", required = false)

    val file        = trailArg[String]("file", descr = "File containing data to be pasted", required = true)

    val name        = opt[String]("name", short = 'n', default = Some(""), descr = "Name to give to the paste", required = false)

    val syntax      = opt[String]("syntax", short = 's', default = Some(""), descr = "Syntax highlighting to use", required = false)

    val help        = opt[Boolean]("help", noshort = true, descr = "Print this help message and exit")

  }

  // Extract argument values
  val lines       = opts.lines()

  val expireTime  = opts.expireTime()

  val privacy     = opts.privacy()

  val syntax      = opts.syntax()

  val name        = opts.name()

  val file        = opts.file()

  /**
    * Makes a paste or prints an error message.
    * 
    */
  def makePaste() = {

    println(s"\npastiesbin v$Version by bmcg running...\n")

    val fileResults  = extractResults(readFile(file, lines), s"Error: Something went wrong while reading file: $file")

    // Check if there was an error while reading the file
    if (hasError(fileResults)) {

        println(s"\n$fileResults\n")
    }

    // No file reading error so try to make the paste
    else {

      val pasteResults = extractResults(issuePaste(fileResults, name,
                                          expireTime, syntax, privacy), "Error: Something went wrong when issuing the HTTP request.")

      println(s"\n$pasteResults\n")
    }
  }

  makePaste()
}
