package org.example.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.exception.TechnicalException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

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
        if (!List.of("GET", "POST").contains(httpMethod)) {
            throw new TechnicalException(httpMethod + " is a supported http method");
        }
        this.httpMethod = httpMethod;
    }

    public JsonNode call() {
        HttpRequest request = buildRequest();
        return doCall(request);
    }

    public JsonNode call(Object payload) {
        try {
            HttpRequest request = buildRequestWithPayload(payload);
            return doCall(request);
        } catch (JsonProcessingException e) {
            throw new TechnicalException("Cannot serialize payload: " + payload);
        }
    }
    
    private JsonNode doCall(HttpRequest request) {
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.body(), JsonNode.class);
        } catch (IOException | InterruptedException e) {
            throw new TechnicalException("Something went wrong while calling " + url, e);
        }
    }

    private HttpRequest buildRequest() {
        HttpRequest.Builder builder = HttpRequest.newBuilder();
        builder.uri(URI.create(url));
        return builder.GET().build();
    }
    
    private HttpRequest buildRequestWithPayload(Object payload) throws JsonProcessingException {
        HttpRequest.Builder builder = HttpRequest.newBuilder();
        builder.uri(URI.create(url));
        ObjectMapper mapper = new ObjectMapper();
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers
                .ofString(mapper.writeValueAsString(payload));
        return builder.POST(body).build();
    }
}
