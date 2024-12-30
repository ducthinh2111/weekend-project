package org.example.executor;

import org.example.resource.Resource;

import java.util.regex.Matcher;

public interface StatementExecutor {
    void execute(String statement, Matcher matcher, Resource resource);
}
