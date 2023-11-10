// https://typelevel.org/sbt-typelevel/faq.html#what-is-a-base-version-anyway
ThisBuild / tlBaseVersion := "0.0" // your current series x.y

ThisBuild / organization := "com.xela85"
ThisBuild / organizationName := "xela85"
ThisBuild / startYear := Some(2023)
ThisBuild / licenses := Seq(License.Apache2)
ThisBuild / developers := List(
  // your GitHub handle and name
  tlGitHubDev("xela85", "Alexandre LEBRUN")
)

// publish to s01.oss.sonatype.org (set to true to publish to oss.sonatype.org instead)
ThisBuild / tlSonatypeUseLegacyHost := false

// publish website from this branch
ThisBuild / tlSitePublishBranch := Some("main")

val Scala3 = "3.3.1"
ThisBuild / scalaVersion := Scala3

lazy val root = tlCrossRootProject.aggregate(core)

val circeVersion = "0.14.5"
lazy val core = crossProject(JVMPlatform, JSPlatform)
  .crossType(CrossType.Pure)
  .in(file("core"))
  .settings(
    name := "decline-fig",
    libraryDependencies ++= Seq(
      "com.monovore" %% "decline" % "2.4.1",
      "io.circe" %% "circe-core" % circeVersion,
      "io.circe" %% "circe-generic" % circeVersion
    )
  )

lazy val docs = project.in(file("site")).enablePlugins(TypelevelSitePlugin)
