package org.example.executor.expression;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.exception.ResourceNotFoundException;
import org.example.exception.TechnicalException;
import org.example.resource.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SelectQueryExecutor implements ExpressionExecutor {

    private static final Pattern SELECT_QUERY_PATTERN = Pattern.compile("select (.*) from (.*)");
    
    @Override
    public Object execute(Matcher matcher, Resource resource) {
        String attribute = matcher.group(1);
        String resourceName = matcher.group(2);
        if (!resourceName.equalsIgnoreCase(resource.getName())) {
            throw new ResourceNotFoundException(resourceName);
        }
        
        JsonNode response = resource.query();
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            if (!attribute.equals("*") && response.isArray()) {
                for (JsonNode node : response) {
                    Map<String, Object> obj = new HashMap<>();
                    obj.put(attribute, mapper.treeToValue(node.get(attribute), Object.class));
                    result.add(obj);
                }
            } else {
                TypeReference<List<Map<String, Object>>> typeRef = new TypeReference<>() {};
                result = mapper.treeToValue(response, typeRef);
            }
        } catch (JsonProcessingException e) {
            throw new TechnicalException("Something went wrong while deserialize for " + response, e);
        }
        return result;
    }

    @Override
    public Pattern getPattern() {
        return SELECT_QUERY_PATTERN;
    }
}
