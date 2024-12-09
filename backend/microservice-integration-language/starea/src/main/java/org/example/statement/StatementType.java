package org.example.statement;

public enum StatementType {
    SELECT("select"),
    INSERT("insert");

    private final String value;

    StatementType(String value) {
        this.value = value;
    }

    public static StatementType fromString(String value) {
        for (StatementType keyword : StatementType.values()) {
            if (keyword.value.equalsIgnoreCase(value)) {
                return keyword;
            }
        }
        return null;
    }
}
