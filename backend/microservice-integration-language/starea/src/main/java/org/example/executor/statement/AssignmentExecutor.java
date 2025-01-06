package org.example.executor.statement;

import org.example.executor.Executor;
import org.example.resource.Resource;
import org.example.storage.Storage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AssignmentExecutor implements StatementExecutor {

    private static final Pattern ASSIGN_STATEMENT_PATTERN = Pattern.compile("(.*) = (.*)");
    
    @Override
    public void execute(Matcher matcher, Resource resource) {
        String variable = matcher.group(1);
        Storage.put(variable, Executor.executeExpression(matcher.group(2), resource));
    }

    @Override
    public Pattern getPattern() {
        return ASSIGN_STATEMENT_PATTERN;
    }
}
