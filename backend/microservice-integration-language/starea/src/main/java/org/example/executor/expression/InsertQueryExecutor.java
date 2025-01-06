package org.example.executor.expression;

import org.example.exception.ResourceNotFoundException;
import org.example.exception.VariableNotFoundException;
import org.example.resource.QueryType;
import org.example.resource.Resource;
import org.example.storage.Storage;
import org.example.utils.VariableMatcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InsertQueryExecutor implements ExpressionExecutor {

    private static final Pattern INSERT_QUERY_PATTERN = Pattern.compile("insert into (.*) data (.*)");
    
    @Override
    public Object execute(Matcher matcher, Resource resource) {
        String resourceName = matcher.group(1);
        if (!resourceName.equalsIgnoreCase(resource.getName())) {
            throw new ResourceNotFoundException(resourceName);
        }
        
        Matcher variableMatcher = VariableMatcher.matcher(matcher.group(2));
        if (!variableMatcher.matches()) {
            throw new VariableNotFoundException(variableMatcher.group(1));
        }
        Object data = Storage.get(variableMatcher.group(1));
        return resource.query(QueryType.INSERT, data);
    }

    @Override
    public Pattern getPattern() {
        return INSERT_QUERY_PATTERN;
    }
}
