package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Resource {
    private final String name;
    private final String url;
    private final HttpClient client;

    public Resource(String name, String url) {
        this.name = name;
        this.url = url;
        this.client = HttpClient.newHttpClient();
    }

    public String getName() {
        return name;
    }

    public void call() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }
}
