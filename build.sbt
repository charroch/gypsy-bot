import sbtappengine.Plugin.{AppengineKeys => gae}

name := "gypsy-bot"

libraryDependencies ++= Seq(
  "javax.servlet" % "servlet-api" % "2.3" % "provided",
  "net.databinder" %% "unfiltered-filter" % "0.5.0",
  "net.databinder" %% "unfiltered-spec" % "0.5.0" % "test",
  "com.google.apis" % "google-api-services-calendar" % "v3-1.3.1-beta",
  "com.google.api-client" % "google-api-client" % "1.6.0-beta",
  "org.specs2" %% "specs2" % "1.7.1" % "test",
  "org.mortbay.jetty" % "jetty" % "6.1.22" % "container"
)

resolvers += "google-api-services" at "http://mavenrepo.google-api-java-client.googlecode.com/hg"

seq(appengineSettings: _*)

unmanagedJars in Compile <++= gae.libUserPath in Compile map {
  libUserPath =>
    val baseDirectories = libUserPath +++ (libUserPath / "orm")
    (baseDirectories * "*.jar").classpath
}

unmanagedJars in Compile <++= gae.libPath in Compile map {
  libPath =>
    ((libPath / "shared") ** "*.jar").classpath
}
