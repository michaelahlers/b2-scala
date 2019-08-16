lazy val `b2` = (project in file("."))
  .configs(IntegrationTest)
  .aggregate(
    `core`,
    `play-json`
  )

lazy val `core` = (project in file("modules") / "core")
//.configs(IntegrationTest)
//.settings(Defaults.itSettings)

lazy val `play-json` = (project in file("modules") / "play-json")
  .dependsOn(`core`)
//.configs(IntegrationTest)
//.settings(Defaults.itSettings)

publish / skip := true
