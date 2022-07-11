# ScalaGAMS
This project demonstrates how to use GAMS Java API in Scala on 64-bit Windows. The example is modified from 
> https://www.gams.com/38/docs/apis/examples_java/Transport1_8java_source.html

In `Transport1.scala`, you can see two ways of invoking GAMS job, one from the model lib in the workspace, and the other from supplied file. 

To run the example
```
sbt "runMain example.Transport1"
```

Expected output:
```
[info] set current project to ScalaGAMS (in build file:***)
[info] running (fork) example.Transport1
[info] Setup workspace completed!
[info] Ran with Default:
[info] x(seattle, new-york):, level    = 50.0, marginal = 0.0
[info] x(seattle, chicago):, level    = 300.0, marginal = 0.0
[info] x(seattle, topeka):, level    = 0.0, marginal = 0.036000000000000004
[info] x(san-diego, new-york):, level    = 275.0, marginal = 0.0
[info] x(san-diego, chicago):, level    = 0.0, marginal = 0.009000000000000008
[info] x(san-diego, topeka):, level    = 275.0, marginal = 0.0
[info] Ran with XPRESS:
[info] x(seattle, new-york):, level    = 0.0, marginal = 4.9E-324
[info] x(seattle, chicago):, level    = 300.0, marginal = 0.0
[info] x(seattle, topeka):, level    = 0.0, marginal = 0.036000000000000004
[info] x(san-diego, new-york):, level    = 325.0, marginal = 0.0
[info] x(san-diego, chicago):, level    = 0.0, marginal = 0.009000000000000008
[info] x(san-diego, topeka):, level    = 275.0, marginal = 0.0
[info] Ran with XPRESS with non-default option:
[info] x(seattle, new-york):, level    = 50.0, marginal = 0.0
[info] x(seattle, chicago):, level    = 300.0, marginal = 0.0
[info] x(seattle, topeka):, level    = 0.0, marginal = 0.036000000000000004
[info] x(san-diego, new-york):, level    = 275.0, marginal = 0.0
[info] x(san-diego, chicago):, level    = 0.0, marginal = 0.009000000000000008
[info] x(san-diego, topeka):, level    = 275.0, marginal = 0.0
[success] Total time: 4 s, completed Mar 20, 2022 5:34:00 PM
```

## Running on macOS

The `Transport1.scala` is also runable on macOS. The only change required is to set the "-Djava.library.path=" variable to "/lib/GAMS_MAC" in build.sbt (just uncomment one of the 2 options). 

These librairies where found in the APIs installed after downloading GAMS 37. Librairies path was the following: "/Library/Frameworks/GAMS.framework/Versions/37/Resources/apifiles/Java" (note that this may not be exactly the same path on another computer.) 