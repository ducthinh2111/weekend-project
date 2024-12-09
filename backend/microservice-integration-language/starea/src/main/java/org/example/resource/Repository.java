package org.example.resource;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class Repository {
    private String url;
    private String httpMethod;
    private Map<String, String> model;
    private final HttpClient client;

    public Repository() {
        this.client = HttpClient.newHttpClient();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public void setModel(Map<String, String> model) {
        this.model = model;
    }

    public void call() throws IOException, InterruptedException {
        HttpRequest request = buildRequest();
        if (request != null) {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        }
    }

    private HttpRequest buildRequest() {
        HttpRequest.Builder builder = HttpRequest.newBuilder();
        builder.uri(URI.create(url));
        if (httpMethod.equals("GET")) {
            return builder.GET().build();
        }
        return null;
    }
}
