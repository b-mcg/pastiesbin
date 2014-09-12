import com.typesafe.sbt.SbtNativePackager._
import NativePackagerKeys._

name := "pastiesbin"

version := "0.0.1"

scalaVersion := "2.11.1"

resolvers += "bintray/bmcg" at "https://dl.bintray.com/bmcg/maven"

libraryDependencies ++= Seq(
  "org.bmcg" %% "pasties" % "0.0.1",
  "org.rogach" %% "scallop" % "0.9.5"
  )

packageArchetype.java_application

// Copy License to final package directory
val downloadLicense = taskKey[File]("Downloads License to packaged directory.")

downloadLicense := {
  val location = target.value / "share" / "LICENSE"
  location.getParentFile.mkdirs()
  IO.copyFile(baseDirectory.value / "LICENSE", location)
  location
}

// Copy README in final package directory
val moveREADME = taskKey[File]("Moves README to packaged directory.")

moveREADME := {
  val readmeLocation = target.value / "share" / "README.md"
  readmeLocation.getParentFile.mkdirs()
  IO.copyFile(baseDirectory.value / "README.md", readmeLocation)
  readmeLocation
}

// Set task value mappings
mappings in Universal += downloadLicense.value -> "LICENSE"

mappings in Universal += moveREADME.value -> "README.md"

lazy val buildSettings = Seq(
  version := "0.0.1-SNAPSHOT",
  organization := "pastiesbin",
  scalaVersion := "2.11"
)
