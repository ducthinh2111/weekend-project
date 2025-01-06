package org.example.resource;

public enum QueryType {
    SELECT("select"),
    INSERT("insert");

    private final String value;

    QueryType(String value) {
        this.value = value;
    }

    public static QueryType fromString(String value) {
        for (QueryType keyword : QueryType.values()) {
            if (keyword.value.equalsIgnoreCase(value)) {
                return keyword;
            }
        }
        throw new IllegalArgumentException("Invalid query type: " + value);
    }

    @Override
    public String toString() {
        return "QueryType{" +
                "value='" + value + '\'' +
                '}';
    }
}
