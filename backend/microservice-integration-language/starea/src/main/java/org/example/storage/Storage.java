package org.example.storage;

import java.util.HashMap;
import java.util.Map;

public class Storage {

    private static final Map<String, Object> storage = new HashMap<>();

    private Storage() {}

    public static void put(String variable, Object data) {
        storage.put(variable, data);
    }

    public static Object get(String variable) {
        return storage.get(variable);
    }
}
