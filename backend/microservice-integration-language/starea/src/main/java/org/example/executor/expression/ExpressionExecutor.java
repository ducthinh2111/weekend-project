package org.example.executor.expression;

import org.example.resource.Resource;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface ExpressionExecutor {
    Optional<Object> execute(Matcher matcher, Resource resource);
    Pattern getPattern();
}
