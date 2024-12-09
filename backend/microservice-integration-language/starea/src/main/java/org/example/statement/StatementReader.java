package org.example.statement;

import org.example.App;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class StatementReader {

    public static Statement read(String filename) throws IOException {
        try (InputStream inputStream = App.class.getClassLoader().getResourceAsStream("language.txt")) {

            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);

            String queryStr = reader.readLine();

            List<String> tokens = List.of(queryStr.split(" "));
            StatementKeywords traversingState = null;
            Statement.Builder statementBuilder = Statement.Builder.newBuilder();
            for (String token : tokens) {
                if (StatementKeywords.fromString(token) == StatementKeywords.SELECT) {
                    traversingState = StatementKeywords.SELECT;
                }
                if (StatementKeywords.fromString(token) == StatementKeywords.FROM) {
                    traversingState = StatementKeywords.FROM;
                }
                if (traversingState == StatementKeywords.SELECT) {
                    statementBuilder
                            .withAttribute(token)
                            .withStatementType(StatementType.SELECT);
                }
                if (traversingState == StatementKeywords.FROM) {
                    statementBuilder.withResource(token);
                }
            }
            return statementBuilder.build();
        }
    }
}
