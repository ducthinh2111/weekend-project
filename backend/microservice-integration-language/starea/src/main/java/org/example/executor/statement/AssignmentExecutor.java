package org.example.executor.statement;

import org.example.executor.Executor;
import org.example.executor.StatementExecutor;
import org.example.resource.Resource;
import org.example.storage.Storage;

import java.util.regex.Matcher;

public class AssignmentExecutor implements StatementExecutor {
    
    @Override
    public void execute(String statement, Matcher matcher, Resource resource) {
        String variable = matcher.group(1);
        Executor.executeExpression(matcher.group(2), resource)
                .ifPresent(result -> Storage.put(variable, result));
    }
}
