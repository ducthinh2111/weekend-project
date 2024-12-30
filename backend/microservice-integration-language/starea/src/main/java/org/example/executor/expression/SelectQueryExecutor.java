package org.example.executor.expression;

import org.example.executor.ExpressionExecutor;
import org.example.resource.QueryType;
import org.example.resource.Resource;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

public class SelectQueryExecutor implements ExpressionExecutor {
    
    @Override
    public List<Map<String, Object>> execute(String statement, Matcher matcher, Resource resource) {
        String attribute = matcher.group(1);
        String resourceName = matcher.group(2);
        if (resourceName.equalsIgnoreCase(resource.getName())) {
            List<Map<String, Object>> result;
            try {
                result = resource.call(QueryType.SELECT);
                if (!attribute.equals("*")) {
                    result = result.stream()
                            .map(e -> Map.of(attribute, e.get(attribute)))
                            .toList();
                }
                return result;
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return List.of();
    }
}
