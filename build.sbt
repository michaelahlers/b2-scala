lazy val `b2` = (project in file("."))
  .configs(IntegrationTest)
  .aggregate(
    `akka-http`,
    `core`,
    `play-json`,
    `testkit`
  )

lazy val `akka-http` = (project in file("modules") / "akka-http")
  .dependsOn(
    `core`,
    `testkit` % Test
  )

lazy val `core` = (project in file("modules") / "core")
//.configs(IntegrationTest)
//.settings(Defaults.itSettings)

lazy val `play-json` = (project in file("modules") / "play-json")
  .dependsOn(
    `core`,
    `testkit` % Test
  )
//.configs(IntegrationTest)
//.settings(Defaults.itSettings)

lazy val `testkit` = (project in file("modules") / "testkit")
  .dependsOn(`core`)

publish / skip := true
