package com.starea.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.HashMap;
import java.util.Map;

public enum LanguageRuntime {
    JDK;

    private static final Map<String, LanguageRuntime> namesMap = new HashMap<>();

    static {
        namesMap.put("openjdk", JDK);
    }

    @JsonCreator
    public static LanguageRuntime fromString(String runtime) {
        return namesMap.get(runtime);
    }

    public String getRuntime() {
        for (Map.Entry<String, LanguageRuntime> entry : namesMap.entrySet()) {
            if (entry.getValue() == this) {
                return entry.getKey();
            }
        }
        return null;
    }
}
