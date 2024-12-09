package org.example;

public enum TraverseState {
    SELECT("select"),
    FROM("from");

    private final String value;

    TraverseState(String value) {
        this.value = value;
    }

    public static TraverseState fromString(String value) {
        for (TraverseState keyword : TraverseState.values()) {
            if (keyword.value.equalsIgnoreCase(value)) {
                return keyword;
            }
        }
        return null;
    }
}
