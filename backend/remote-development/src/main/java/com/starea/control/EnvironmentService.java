package com.starea.control;

import com.starea.model.EnvironmentInfo;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

@Dependent
public class EnvironmentService {

    @Inject
    Logger logger;

    public void createEnvironment(EnvironmentInfo environmentInfo) {
        try {
            String containerName = "openjdk-container";
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "docker", "run",
                    "--name", containerName,
                    "-d",
                    "-p", "4200:8080",
                    "openjdk-runtime"
            );
            Process process = processBuilder.start();
            String output = readOutputMessage(process.getInputStream());
            String errOutput = readOutputMessage(process.getErrorStream());
            logger.info(output);
            logger.error(errOutput);
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        logger.info("Runtime " + environmentInfo.runtime().getRuntime());
    }

    private String readOutputMessage(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append(System.lineSeparator());
        }
        return output.toString().trim();
    }
}
