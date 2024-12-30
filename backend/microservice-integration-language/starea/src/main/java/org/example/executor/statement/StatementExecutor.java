package org.example.executor.statement;

import org.example.resource.Resource;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface StatementExecutor {
    void execute(Matcher matcher, Resource resource);
    Pattern getPattern();
}
