package org.example;

public class Resource {
    private final String name;
    private final String url;

    public Resource(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
