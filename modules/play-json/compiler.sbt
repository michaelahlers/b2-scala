/**
 * Fix compilation where Play Json macro inception is used.
 */
scalacOptions --=
  "-Wunused:locals" ::
    "-Wunused:privates" ::
    "-Ywarn-unused:locals" ::
    "-Ywarn-unused:privates" ::
    Nil

/**
 * Need not be so strict with tests.
 */
Test / scalacOptions --=
  "-Wunused:implicits" ::
    "-Wunused:imports" ::
    "-Wunused:locals" ::
    "-Wunused:params" ::
    "-Wunused:privates" ::
    "-Ywarn-unused-import" ::
    "-Ywarn-unused:implicits" ::
    "-Ywarn-unused:imports" ::
    "-Ywarn-unused:locals" ::
    "-Ywarn-unused:params" ::
    "-Ywarn-unused:privates" ::
    Nil
