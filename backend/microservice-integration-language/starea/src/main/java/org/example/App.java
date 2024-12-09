package org.example;

import org.example.resource.Resource;
import org.example.resource.ResourceReader;
import org.example.statement.Statement;
import org.example.statement.StatementReader;

import java.io.IOException;
import java.net.URISyntaxException;

public class App {
    public static void main(String[] args) throws IOException, URISyntaxException {

        Statement statement = StatementReader.read("language.txt");
        Resource resource = ResourceReader.read("resource.txt");
        System.out.println("");
    }
}
