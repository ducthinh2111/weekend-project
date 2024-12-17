package org.example;

import org.example.reader.LanguageExecutor;
import org.example.resource.Resource;
import org.example.reader.ResourceReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

public class App {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        Resource resource = ResourceReader.read("resource.txt");

        try (InputStream inputStream = App.class.getClassLoader().getResourceAsStream("language.txt")) {
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                LanguageExecutor.execute(line, resource);
            }
        }
    }
}
