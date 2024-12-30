package org.example.executor;

import org.example.resource.Resource;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

public interface ExpressionExecutor {
    List<Map<String, Object>> execute(String statement, Matcher matcher, Resource resource);
}
