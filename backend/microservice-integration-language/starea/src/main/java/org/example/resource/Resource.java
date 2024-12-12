package org.example.resource;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import org.example.statement.StatementType;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Resource {
    private String name;

    private Map<StatementType, Repository> repositoryMap = new HashMap<>();

    @JsonAnySetter
    public void addRepository(String key, Repository repository) {
        repositoryMap.put(StatementType.fromString(key), repository);
    }

    public Resource() {};

    public void setName(String name) {
        this.name = name;
    }

    public void setRepositoryMap(Map<StatementType, Repository> repositoryMap) {
        this.repositoryMap = repositoryMap;
    }

    public String getName() {
        return name;
    }

    public List<Map<String, Object>> call(StatementType statementType) throws IOException, InterruptedException {
        Repository repository = repositoryMap.get(statementType);
        if (repository != null) {
            return repository.call();
        }
        return null;
    }
}
