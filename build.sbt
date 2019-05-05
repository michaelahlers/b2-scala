lazy val root =
  project
    .aggregate(core)

lazy val core = project in (file("modules") / "core")

publish / skip := true
