package org.example.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.query.Query;
import org.example.query.QueryType;
import org.example.resource.Resource;
import org.example.storage.Storage;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LanguageExecutor {
    private static final Pattern ASSIGN_STATEMENT_PATTERN = Pattern.compile("(.*) = (.*)");
    private static final Pattern SELECT_QUERY_PATTERN = Pattern.compile("select (.*) from (.*)");
    private static final Pattern PRINT_METHOD_PATTERN = Pattern.compile("print\\((.*)\\)");
    private static final Pattern VARIABLE_PATTERN = Pattern.compile("\\$(.*)");

    public static void execute(String statement, Resource resource) throws IOException, InterruptedException {
        Matcher assignStatementMatcher = ASSIGN_STATEMENT_PATTERN.matcher(statement);

        if (assignStatementMatcher.find()) {
            String variable = assignStatementMatcher.group(1);
            Query query = detectQuery(assignStatementMatcher.group(2));

            Storage.put(variable, query.run(resource));
        }

        Matcher printMethodMatcher = PRINT_METHOD_PATTERN.matcher(statement);

        if (printMethodMatcher.find()) {
            String argument = printMethodMatcher.group(1);
            Matcher variableMatcher = VARIABLE_PATTERN.matcher(argument);
            if (variableMatcher.find()) {
                List<Map<String, Object>> data = Storage.get(variableMatcher.group(1));
                ObjectMapper mapper = new ObjectMapper();
                System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data));
            }
        }
    }

    private static Query detectQuery(String str) {
        Matcher matcher = SELECT_QUERY_PATTERN.matcher(str);
        if (matcher.find()) {
            return Query.Builder.newBuilder()
                    .withQueryType(QueryType.SELECT)
                    .withAttribute(matcher.group(1))
                    .withResource(matcher.group(2))
                    .build();
        }
        throw new IllegalArgumentException("Invalid query: " + str);
    }
}

