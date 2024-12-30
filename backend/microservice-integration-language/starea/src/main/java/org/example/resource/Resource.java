package org.example.resource;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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

    public void setRepositoryMap(Map<QueryType, Repository> repositoryMap) {
        this.repositoryMap = repositoryMap;
    }

    public String getName() {
        return name;
    }

    public List<Map<String, Object>> call(QueryType queryType) throws IOException, InterruptedException {
        Repository repository = repositoryMap.get(queryType);
        if (repository != null) {
            return repository.call();
        }
        return null;
    }
}
