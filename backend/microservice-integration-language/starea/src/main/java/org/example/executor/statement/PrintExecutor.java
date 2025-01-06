package org.example.executor.statement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.exception.VariableNotFoundException;
import org.example.resource.Resource;
import org.example.storage.Storage;
import org.example.utils.VariableMatcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrintExecutor implements StatementExecutor {

    private static final Pattern PRINT_METHOD_PATTERN = Pattern.compile("print\\((.*)\\)");
    
    @Override
    public void execute(Matcher matcher, Resource resource) {
        String argument = matcher.group(1);
        Matcher variableMatcher = VariableMatcher.matcher(argument);
        if (variableMatcher.find()) {
            Object data = Storage.get(variableMatcher.group(1));
            if (data == null) {
                throw new VariableNotFoundException(variableMatcher.group(1));
            }
            ObjectMapper mapper = new ObjectMapper();
            try {
                System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Pattern getPattern() {
        return PRINT_METHOD_PATTERN;
    }
}
