package org.example.statement;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.resource.Resource;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class Statement {
    private final StatementType statementType;
    private final String attribute;
    private final String resource;

    public Statement(StatementType statementType, String attribute, String resource) {
        this.statementType = statementType;
        this.attribute = attribute;
        this.resource = resource;
    }

    public static class Builder {
        private StatementType statementType;
        private String attribute;
        private String resource;

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder withStatementType(StatementType statementType) {
            this.statementType = statementType;
            return this;
        }

        public Builder withAttribute(String attribute) {
            this.attribute = attribute;
            return this;
        }

        public Builder withResource(String resource) {
            this.resource = resource;
            return this;
        }

        public Statement build() {
            requireNonNull(statementType, "statementType must not be null");
            requireNonNull(attribute, "attribute must not be null");
            requireNonNull(resource, "resource must not be null");
            return new Statement(statementType, attribute, resource);
        }
    }

    public void run(Resource resource) throws IOException, InterruptedException {
        if (this.resource.equalsIgnoreCase(resource.getName())) {
            List<Map<String, Object>> result = resource.call(statementType);
            if (!this.attribute.equals("*")) {
                result = result.stream()
                        .map(e -> Map.of(this.attribute, e.get(this.attribute)))
                        .toList();
            }
            ObjectMapper mapper = new ObjectMapper();
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
        }
    }
}
