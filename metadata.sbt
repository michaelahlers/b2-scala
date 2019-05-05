name := "b2-scala"
description := "Scala libraries for Backblaze B2."

ThisBuild / organization := "ahlers"
ThisBuild / organizationName := "Ahlers Consulting"

ThisBuild / homepage := Some(new URL("http://b2-scala.github.io"))
ThisBuild / startYear := Some(2019)

ThisBuild / developers :=
    Developer("michaelahlers", "Michael Ahlers", "michael@ahlers.consulting", url("http://github.com/michaelahlers")) ::
    Nil

ThisBuild / scmInfo :=
  Some(ScmInfo(
    browseUrl = url("https://github.com/michaelahlers/b2-scala"),
    connection = "https://github.com/michaelahlers/b2-scala.git",
    devConnection = Some("git@github.com:michaelahlers/b2-scala.git")
  ))

ThisBuild / licenses += "MIT" -> new URL("http://opensource.org/licenses/MIT")


