package org.example;

import org.example.executor.Executor;
import org.example.resource.Resource;
import org.example.resource.ResourceReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {

    public static void main(String[] args) throws IOException, URISyntaxException {
        Resource resource = ResourceReader.read("resource.txt");

        try (InputStream inputStream = App.class.getClassLoader().getResourceAsStream("language.txt")) {
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);

            List<String> statements = new ArrayList<>();
            String unfinishedStatement = "";
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                line = line.trim();
                line = unfinishedStatement + " " + line;
                List<String> splitedBySemicolon = Arrays.stream(line.split(";"))
                        .map(String::trim)
                        .toList();
                
                if (line.endsWith(";")) {
                    unfinishedStatement = "";
                    statements.addAll(splitedBySemicolon);
                } else {
                    statements.addAll(splitedBySemicolon.subList(0, splitedBySemicolon.size() - 1));
                    unfinishedStatement = splitedBySemicolon.getLast();
                }
            }
            
            for (String statement : statements) {
                String correctedStatement = statement.replaceAll(" +", " ");
                System.out.println(correctedStatement);
                Executor.executeStatement(correctedStatement, resource);
            }
        }
    }
}
