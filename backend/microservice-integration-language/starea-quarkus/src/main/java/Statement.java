package org.example;

public class Statement {
    private StatementType statementType;
    private String attribute;
    private String resource;

    public Statement() {}

    public Statement(String attribute, String resource) {
        this.attribute = attribute;
        this.resource = resource;
    }

    public String getAttribute() {
        return attribute;
    }

    public String getResource() {
        return resource;
    }

    public StatementType getStatementType() {
        return statementType;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public void setStatementType(StatementType statementType) {
        this.statementType = statementType;
    }

    @Override
    public String toString() {
        return "Statement{" +
                "statementType=" + statementType +
                ", attribute='" + attribute + '\'' +
                ", resource='" + resource + '\'' +
                '}';
    }
}
