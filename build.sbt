lazy val `b2` = (project in file("."))
  .configs(IntegrationTest)
  .aggregate(
    `core`,
    `core-play-json`
  )

lazy val `core` = (project in file("modules") / "core")
  .configs(IntegrationTest)

lazy val `core-play-json` = (project in file("modules") / "core-play-json")
  .configs(IntegrationTest)
  .dependsOn(`core`)

publish / skip := true
