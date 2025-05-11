package com.starea.control

import jakarta.enterprise.context.Dependent
import java.io.BufferedReader
import java.io.File
import java.io.FileWriter
import java.io.InputStream
import java.io.InputStreamReader

@Dependent
class ExecutorService {

    fun execute(code: String): String {
        print("Executing $code....")
        val directoryName = "/Users/starea/dev/codes"
        val directory = File(directoryName)
        if (!directory.exists()) {
            directory.mkdirs()
        }
        val file = File(directory, "kotlin.kt")
        if (!file.exists()) {
            file.createNewFile()
        }
        val fileWriter = FileWriter(file)
        fileWriter.use {
            it.write(code)
        }
        val compileProcessBuilder = ProcessBuilder(
            "/Users/starea/.sdkman/candidates/kotlin/current/bin/kotlinc",
            "/Users/starea/dev/codes/kotlin.kt",
            "-include-runtime",
            "-d",
            "/Users/starea/dev/codes/kotlin.jar"
        )
        val compileProcess = compileProcessBuilder.redirectErrorStream(true).start()
        val output = StringBuilder()
        // output.append(readOutput(compileProcess.inputStream))
        compileProcess.waitFor()
        val runProcessBuilder = ProcessBuilder(
            "java",
            "-jar",
            "/Users/starea/dev/codes/kotlin.jar"
        )
        val runProcess = runProcessBuilder.redirectErrorStream(true).start()
        output.append('\n')
        output.append(readOutput(runProcess.inputStream))
        runProcess.waitFor()
        return output.toString()
    }

    private fun readOutput(inputStream: InputStream): String {
        val output = StringBuilder()
        var line: String?
        val reader = BufferedReader(InputStreamReader(inputStream))
        while (reader.readLine().also { line = it } != null) {
            output.append(line).append('\n')
        }
        return output.toString().trim()
    }
}