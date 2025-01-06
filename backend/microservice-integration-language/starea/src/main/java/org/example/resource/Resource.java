package org.example.resource;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.databind.JsonNode;
import org.example.exception.TechnicalException;

import java.util.HashMap;
import java.util.Map;

public class Resource {
    
    private String name;
    private Map<QueryType, Repository> repositoryMap = new HashMap<>();

    @JsonAnySetter
    public void addRepository(String key, Repository repository) {
        repositoryMap.put(QueryType.fromString(key), repository);
    }

    public Resource() {};

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public JsonNode query() {
        Repository repository = repositoryMap.get(QueryType.SELECT);
        if (repository == null) {
            throw new TechnicalException("No repository found for " + QueryType.SELECT);
        }
        return repository.call();
    }

    public JsonNode query(QueryType queryType, Object payload) {
        if (queryType == QueryType.SELECT) {
            throw new IllegalArgumentException(QueryType.SELECT + " cannot be called with payload");
        }
        Repository repository = repositoryMap.get(queryType);
        if (repository == null) {
            throw new TechnicalException("No repository found for " + queryType);
        }
        return repository.call(payload);
    }
}
