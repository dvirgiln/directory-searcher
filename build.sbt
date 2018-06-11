lazy val akkaHttpVersion = "10.0.11"
lazy val akkaVersion    = "2.5.11"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization    := "com.david",
      scalaVersion    := "2.12.4"
    )),
    name := "directory-searcher",
    version := "0.0.1-SNAPSHOT",
    libraryDependencies ++= Seq(
      "org.scalatest"     %% "scalatest"            % "3.0.1"         % Test
    )
  )


mainClass in Compile := Some("com.david.searcher.QuickstartApp")

