ThisBuild / scalaVersion := "2.13.0"
ThisBuild / crossScalaVersions += "2.12.8"

/* TODO: Set Cats release version. */

ThisBuild / libraryDependencies ++=
  "com.github.pathikrit" %% "better-files" % "3.8.0" ::
    "org.typelevel" %% "cats-core" % "2.1.1" ::
    "org.scalactic" %% "scalactic" % "3.1.0" ::
    Nil

ThisBuild / libraryDependencies ++=
  "com.softwaremill.diffx" %% "diffx-scalatest" % "0.3.12" % Test ::
    "com.github.alexarchambault" %% "scalacheck-shapeless_1.14" % "1.2.3" % Test ::
    "org.typelevel" %% "cats-laws" % "2.1.1" % Test ::
    "org.typelevel" %% "cats-testkit" % "2.1.1" % Test ::
    "org.scalacheck" %% "scalacheck" % "1.14.2" % Test ::
    "org.scalamock" %% "scalamock" % "4.4.0" % Test ::
    "org.scalatest" %% "scalatest" % "3.1.0" % Test ::
    "org.scalatestplus" %% "scalatestplus-scalacheck" % "1.0.0-M2" % Test ::
    Nil

//ThisBuild / libraryDependencies ++=
//  "org.scalatest" %% "scalatest" % "3.0.8" % IntegrationTest ::
//    Nil
