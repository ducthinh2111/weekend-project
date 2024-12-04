package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class App {

    public static void main(String[] args) throws IOException, URISyntaxException {
        Path filePath = Paths.get(App.class.getClassLoader().getResource("test.txt").toURI());
        String content = Files.readString(filePath, StandardCharsets.UTF_8);

        ObjectMapper mapper = new ObjectMapper();
        Template template = mapper.readValue(content, Template.class);

        List<Student> students = mapper.convertValue(template.getValue(),
                mapper.getTypeFactory().constructFromCanonical(template.getType()));
        System.out.println(students.getFirst().getName());
    }
}
