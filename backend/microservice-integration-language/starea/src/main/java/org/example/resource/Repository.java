package org.example.resource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class Repository {
    private String url;
    private String httpMethod;
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

    public List<Map<String, Object>> call() throws IOException, InterruptedException {
        HttpRequest request = buildRequest();
        if (request != null) {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Map<String, Object>>> typeReference = new TypeReference<>() {};
            return mapper.readValue(response.body(), typeReference);
        }
        return null;
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
