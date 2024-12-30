package org.example.executor;

import org.example.executor.expression.JsonExecutor;
import org.example.executor.expression.SelectQueryExecutor;
import org.example.executor.statement.AssignmentExecutor;
import org.example.executor.statement.PrintExecutor;
import org.example.resource.Resource;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Executor {
    
    private static final Map<Pattern, StatementExecutor> STATEMENT_EXECUTOR_BY_PATTERN = new HashMap<>();
    private static final Map<Pattern, ExpressionExecutor> EXPRESSION_EXECUTOR_BY_PATTERN = new HashMap<>();
    
    static {
        registerStatementExecutor(new AssignmentExecutor());
        registerStatementExecutor(new PrintExecutor());
        
        registerExpressionExecutor(new SelectQueryExecutor());
        registerExpressionExecutor(new JsonExecutor());
    }
    
    private static void registerStatementExecutor(StatementExecutor statementExecutor) {
        STATEMENT_EXECUTOR_BY_PATTERN.put(statementExecutor.getPattern(), statementExecutor);
    }

    private static void registerExpressionExecutor(ExpressionExecutor expressionExecutor) {
        EXPRESSION_EXECUTOR_BY_PATTERN.put(expressionExecutor.getPattern(), expressionExecutor);
    }
    
    public static void executeStatement(String statement, Resource resource) {
        for (Map.Entry<Pattern, StatementExecutor> entry : STATEMENT_EXECUTOR_BY_PATTERN.entrySet()) {
            Matcher matcher = entry.getKey().matcher(statement);
            if (matcher.matches()) {
                entry.getValue().execute(matcher, resource);
            }
        }
    }
    
    public static Optional<Object> executeExpression(String expression, Resource resource) {
        for (Map.Entry<Pattern, ExpressionExecutor> entry : EXPRESSION_EXECUTOR_BY_PATTERN.entrySet()) {
            Matcher expressionMatcher = entry.getKey().matcher(expression);
            if (expressionMatcher.matches()) {
                return entry.getValue().execute(expressionMatcher, resource);
            }
        }
        return Optional.empty();
    }
    
}
