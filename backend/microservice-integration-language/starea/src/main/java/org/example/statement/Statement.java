package org.example.statement;

import org.example.resource.Resource;

import java.io.IOException;

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
            if (statementType == StatementType.SELECT) {
                resource.call(statementType);
            }
        }
    }
}
