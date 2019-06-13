import sbt._
import Keys._
import sbtrelease.ReleasePlugin._

lazy val scalaFaker = (project in file("."))
  .settings(
    name := "scala-faker",
    organization := "it.bitbl",
    crossScalaVersions := Seq("2.10.6", "2.11.11", "2.12.8", "2.13.0"),
    scalaVersion := crossScalaVersions.value.last,

    sbtPlugin := false,

    publishArtifact in (Compile, packageDoc) := false,
    scalacOptions ++= Seq("-deprecation", "-Xcheckinit", "-encoding", "utf8", "-g:vars", "-unchecked", "-optimize"),
    parallelExecution := true,
    parallelExecution in Test := true,
    homepage := Some(new java.net.URL("https://github.com/bitblitconsulting/scala-faker/")),
    description := "A library for generating fake data.",

    publishTo := Some(
      if (isSnapshot.value)
        Opts.resolver.sonatypeSnapshots
      else
        Opts.resolver.sonatypeStaging
    ),

    licenses := Seq("APL2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt")),

    scmInfo := Some(
      ScmInfo(
        url("https://github.com/bitblitconsulting/scala-faker"),
        "scm:git@github.com:bitblitconsulting/scala-faker.git"
      )
    ),

    developers := List(
      Developer(id="j1mr10rd4n", name="Jim Riordan", email="jim@j1mr10rd4n.info", url=url("https://github.com/j1mr10rd4n")),
      Developer(id="justwrote", name="Just Wrote", email="unknown", url=url("https://github.com/justwrote"))
    ),

    initialCommands := "import faker._",

    libraryDependencies ++= Seq(
      "org.yaml" % "snakeyaml" % "1.13",
      scalaTest.value
    ),

    // enable publishing the main API jar
    publishArtifact in (Compile, packageDoc) := true
  )

  def scalaTest = Def.setting {
    def st(scalaVersion: String, projectVersion: String) =
      "org.scalatest" % ("scalatest_" + scalaVersion) % projectVersion % "test"

    scalaBinaryVersion.value match {
      case "2.10" => st("2.10", "2.1.7")
      case "2.11" => st("2.11", "2.1.7")
      case "2.12" => st("2.12", "3.0.8")
      case "2.13" => st("2.13", "3.0.8")
      case _ => sys.error("ScalaTest not supported for scala version %s!" format version) 
    }
  }
