package org.example.statement;

public enum StatementKeywords {
    SELECT("select"),
    INSERT("insert"),
    FROM("from");

    private final String value;

    StatementKeywords(String value) {
        this.value = value;
    }

    public static StatementKeywords fromString(String value) {
        for (StatementKeywords keyword : StatementKeywords.values()) {
            if (keyword.value.equalsIgnoreCase(value)) {
                return keyword;
            }
        }
        return null;
    }
}
