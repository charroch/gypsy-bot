import sbtappengine.Plugin.{AppengineKeys => gae}

name := "gypsy-bot"

libraryDependencies ++= Seq(
  "javax.servlet" % "servlet-api" % "2.3" % "provided",
   "net.databinder" %% "unfiltered-filter" % "0.5.0",
    "net.databinder" %% "unfiltered-spec" % "0.5.0" % "test",
  "org.mortbay.jetty" % "jetty" % "6.1.22" % "container"
)

seq(appengineSettings: _*)

unmanagedJars in Compile <++= gae.libUserPath in Compile map { libUserPath =>
 val baseDirectories = libUserPath +++ (libUserPath / "orm")
 (baseDirectories * "*.jar").classpath
}

unmanagedJars in Compile <++= gae.libPath in Compile map { libPath =>
 ((libPath / "shared") ** "*.jar").classpath
}
