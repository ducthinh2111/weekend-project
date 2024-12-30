package org.example.executor.expression;

import org.example.resource.QueryType;
import org.example.resource.Resource;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SelectQueryExecutor implements ExpressionExecutor {

    private static final Pattern SELECT_QUERY_PATTERN = Pattern.compile("select (.*) from (.*)");
    
    @Override
    public Optional<Object> execute(Matcher matcher, Resource resource) {
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
                return Optional.of(result);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return Optional.empty();
    }

    @Override
    public Pattern getPattern() {
        return SELECT_QUERY_PATTERN;
    }
}
