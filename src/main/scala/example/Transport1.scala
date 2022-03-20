package example
  
import java.io._
import com.gams.api._

object Transport1 {

    def main(args: Array[String]): Unit = {
        var ws: GAMSWorkspace = null
        // check workspace info from command line arguments
        if (args.length > 0) {  
            var wsInfo: GAMSWorkspaceInfo = new GAMSWorkspaceInfo()
            wsInfo.setSystemDirectory(args(0))
            // create GAMSWorkspace "ws" with user-specified system directory and the default working directory 
            // (the directory named with current date and time under System.getProperty("java.io.tmpdir"))
            ws = new GAMSWorkspace(wsInfo)
        } else {
            // create GAMSWorkspace "ws" with default system directory and default working directory 
            // (the directory named with current date and time under System.getProperty("java.io.tmpdir"))
            ws = new GAMSWorkspace()
        }

        // create GAMSJob "t1" from "trnsport" model in GAMS Model Libraries
        // val t1: GAMSJob = ws.addJobFromGamsLib("trnsport100")

        // run GAMSJob from local file
        val currentDirectory = new java.io.File(".").getCanonicalPath
        val t1: GAMSJob = ws.addJobFromFile(f"$currentDirectory//GAMS_MODELS//transport.gms")
        // run GAMSJob "t1"
        t1.run()

        // retrieve GAMSVariable "x" from GAMSJob's output databases
        println("Ran with Default:")
        val x: GAMSVariable = t1.OutDB().getVariable("x")
        x.forEach(rec => {
            print("x(" + rec.getKey(0) + ", " + rec.getKey(1) + "):")
            print(", level    = " + rec.getLevel())
            println(", marginal = " + rec.getMarginal())
        })

        // create GAMSOptions "opt1"
        val opt1: GAMSOptions = ws.addOptions()
        // set all model types of "opt1" for "xpress"
        opt1.setAllModelTypes("xpress")
        // run GAMSJob "t1" with GAMSOptions "opt1"
        t1.run(opt1)

        // retrieve GAMSVariable "x" from GAMSJob's output databases
        val db1: GAMSDatabase = t1.OutDB()
        println("Ran with XPRESS:")
        db1.getVariable("x").forEach(rec => {
            print("x(" + rec.getKey(0) + ", " + rec.getKey(1) + "):")
            print(", level    = " + rec.getLevel())
            println(", marginal = " + rec.getMarginal())
        })

        // write file "xpress.opt" under GAMSWorkspace's working directory
        try {
            val optFile: BufferedWriter = new BufferedWriter(new FileWriter(ws.workingDirectory() + GAMSGlobals.FILE_SEPARATOR + "xpress.opt"))
            optFile.write("algorithm=barrier")
            optFile.close()
        } catch {
            case e: IOException =>
              e.printStackTrace()
              System.exit(-1)
        }

        // create GAMSOptions "opt2"
        val opt2: GAMSOptions = ws.addOptions()
        // set all model types of "opt2" for "xpress"
        opt2.setAllModelTypes( "xpress" )
        // for "opt2", use "xpress.opt" as solver's option file
        opt2.setOptFile(1)

        try {
          // run GAMSJob "t2" with GAMSOptions "opt2" and capture log into "transport1_xpress.log". 
          val output: PrintStream = new PrintStream(new File(ws.workingDirectory() + GAMSGlobals.FILE_SEPARATOR +"transport1_xpress.log")) 
          t1.run(opt2, output)
        } catch {
            case e: FileNotFoundException =>
                // run GAMSJob "t2" with GAMSOptions "opt2" and log is written to standard output
                t1.run(opt2)
        }

        // retrieve GAMSVariable "x" from GAMSJob's output databases
        val db2: GAMSDatabase = t1.OutDB()
        println("Ran with XPRESS with non-default option:")
        db2.getVariable("X").forEach(rec => {
            print("x(" + rec.getKey(0) + ", " + rec.getKey(1) + "):")
            print(", level    = " + rec.getLevel())
            println(", marginal = " + rec.getMarginal())            
        })

        // dispose option and database
        opt1.dispose()
        opt2.dispose()
        db1.dispose()
        db2.dispose()
        // cleanup GAMSWorkspace's working directory
        cleanup(ws.workingDirectory())
        // terminate program
        System.exit(0)
    }

    def cleanup(directory: String): Unit = {
        val directoryToDelete: File = new File(directory)
        val files: Array[String] = directoryToDelete.list()
        for (file <- files) {
            val fileToDelete: File = new File(directoryToDelete, file)
            try {
                fileToDelete.delete()
            } catch{
                case e: Exception => 
                    e.printStackTrace()
            }
        }
        try {
          directoryToDelete.delete()
        } catch {
            case e: Exception =>
                e.printStackTrace()
        }
    }
}