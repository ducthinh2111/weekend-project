package org.example;

import java.io.IOException;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        String queryStr = "select * from Todos";
        List<String> tokens = List.of(queryStr.split(" "));
        TraverseState traversingState = null;
        Statement.Builder statementBuilder = Statement.Builder.newBuilder();
        for (String token : tokens) {
            if (TraverseState.fromString(token) == TraverseState.SELECT) {
                traversingState = TraverseState.SELECT;
            }
            if (TraverseState.fromString(token) == TraverseState.FROM) {
                traversingState = TraverseState.FROM;
            }
            if (traversingState == TraverseState.SELECT) {
                statementBuilder
                        .withAttribute(token)
                        .withStatementType(StatementType.SELECT);
            }
            if (traversingState == TraverseState.FROM) {
                statementBuilder.withResource(token);
            }
        }
        Statement statement = statementBuilder.build();
        statement.run();
    }
}
