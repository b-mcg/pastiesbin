package pastiesbin

// Imports
import pasties.PasteBinAPI
import scala.util.{Try, Success, Failure}
import scala.io.Source.fromFile

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
  * Contains functions necessary for
  * creating a new paste, reading a source
  * file, and handling errors.
  *
  */
object Paste {

  /**
    * Returns response from issuing
    * a request to make a new paste.
    *
    * @param codeToPaste: Code/text to paste
    * @param nameOfPaste: Name to give to the paste
    * @param expireTime: When the paste should expire in availability
    * @param syntaxHighlight: What code style it is and should be set for syntax highlighting
    * @param privacy: The privacy level to give to the paste
    * @return Success[String] or Failure[Throwable]
    */
  def issuePaste(codeToPaste: String, nameOfPaste: String, expireTime: String,
                  syntaxHighlight: String, privacy: String): Try[String] = {

    // Instantiate the API Interface and paste the code
    val APIInterface = PasteBinAPI("Your developer key here")

    APIInterface.makePaste(codeToPaste, nameOfPaste, expireTime, syntaxHighlight, privacy)
  }

  /**
    * Returns contents of a file.
    *
    * @param fileName: Filename to read
    * @param lines: List of two elements with the line numbers to read from fileName
    * @return Success[String] or Failure[Throwable]
    *
    */
  def readFile(fileName: String, lines: List[Int]): Try[String] = {

    // Open the file for reading
    val bufferedFile = fromFile(fileName)

    if (lines.isEmpty) {
      
      val fileContents = Try(bufferedFile.mkString)

      // Close the file
      bufferedFile.close()

      fileContents
    }

    else {

      val contents = bufferedFile.getLines.toStream

      // Close the file
      bufferedFile.close()

      Try(contents.slice(lines.head - 1, lines.last).mkString("\n"))
    }
  }

  /**
    * Returns extracted result
    * from a Try[String] argument
    * if Success or error message if
    * a failure.
    *
    * @param results: Either a Success[String] or Failure[Throwable] from some operation
    * @param message: Error message to return if a Failure[Throwable]
    * @return extracted result from the Try operation or an error message
    *
    */
  def extractResults(results: Try[String], message: String) = results match {

    case Success(value) => value

    case Failure(err)   => message
  }

  /**
    * Returns true if a given string
    * contains the word Error, false
    * otherwise.
    *
    * @param results: String result from a previous operation
    * @return true is results contains the word 'Error' false otherwise
    *
    */
  def hasError(results: String): Boolean = results.contains("Error")
}
