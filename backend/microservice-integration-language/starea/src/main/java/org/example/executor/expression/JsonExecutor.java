package org.example.executor.expression;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.resource.Resource;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonExecutor implements ExpressionExecutor {

    private static final Pattern JSON_PATTERN = Pattern.compile("\\{.*}");
    
    @Override
    public Object execute(Matcher matcher, Resource resource) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(matcher.group(), Object.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Pattern getPattern() {
        return JSON_PATTERN;
    }
}
