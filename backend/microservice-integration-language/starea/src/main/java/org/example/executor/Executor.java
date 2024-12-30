package org.example.executor;

import org.example.executor.expression.SelectQueryExecutor;
import org.example.executor.statement.AssignmentExecutor;
import org.example.executor.statement.PrintExecutor;
import org.example.resource.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Executor {
    private static final Pattern SELECT_QUERY_PATTERN = Pattern.compile("select (.*) from (.*)");
    private static final Pattern ASSIGN_STATEMENT_PATTERN = Pattern.compile("(.*) = (.*)");
    private static final Pattern PRINT_METHOD_PATTERN = Pattern.compile("print\\((.*)\\)");

    private static final Map<Pattern, StatementExecutor> STATEMENT_EXECUTOR_BY_PATTERN = new HashMap<>() {{
        put(ASSIGN_STATEMENT_PATTERN, new AssignmentExecutor());
        put(PRINT_METHOD_PATTERN, new PrintExecutor());
    }};
    
    private static final Map<Pattern, ExpressionExecutor> EXPRESSION_EXECUTOR_BY_PATTERN = new HashMap<>() {{
        put(SELECT_QUERY_PATTERN, new SelectQueryExecutor());
    }};

    public static void executeStatement(String statement, Resource resource) {
        for (Map.Entry<Pattern, StatementExecutor> entry : STATEMENT_EXECUTOR_BY_PATTERN.entrySet()) {
            Matcher matcher = entry.getKey().matcher(statement);
            if (matcher.matches()) {
                entry.getValue().execute(statement, matcher, resource);
            }
        }
    }
    
    public static Optional<List<Map<String, Object>>> executeExpression(String expression, Resource resource) {
        for (Map.Entry<Pattern, ExpressionExecutor> entry : EXPRESSION_EXECUTOR_BY_PATTERN.entrySet()) {
            Matcher expressionMatcher = entry.getKey().matcher(expression);
            if (expressionMatcher.matches()) {
                return Optional.of(entry.getValue().execute(expression, expressionMatcher, resource));
            }
        }
        return Optional.empty();
    }
    
}
