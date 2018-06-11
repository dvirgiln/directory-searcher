
val meta = """META.INF(.)*""".r
val assemblySettings=Seq(assemblyMergeStrategy in assembly := {
  case PathList("javax", "servlet", xs@_*) => MergeStrategy.first
  case PathList(ps@_*) if ps.last endsWith ".html" => MergeStrategy.first
  case n if n.startsWith("reference.conf") => MergeStrategy.concat
  case n if n.endsWith(".conf") => MergeStrategy.concat
  case meta(_) => MergeStrategy.discard
  case x => MergeStrategy.first

})

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
enablePlugins(JavaAppPackaging)

mainClass in Compile := Some("com.david.searcher.QuickstartApp")

