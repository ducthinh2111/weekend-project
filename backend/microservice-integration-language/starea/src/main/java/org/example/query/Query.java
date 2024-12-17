package org.example.query;

import org.example.resource.Resource;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class Query {
    private final QueryType queryType;
    private final String attribute;
    private final String resource;

    public Query(QueryType queryType, String attribute, String resource) {
        this.queryType = queryType;
        this.attribute = attribute;
        this.resource = resource;
    }

    public static class Builder {
        private QueryType queryType;
        private String attribute;
        private String resource;

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder withQueryType(QueryType queryType) {
            this.queryType = queryType;
            return this;
        }

        public Builder withAttribute(String attribute) {
            this.attribute = attribute;
            return this;
        }

        public Builder withResource(String resource) {
            this.resource = resource;
            return this;
        }

        public Query build() {
            requireNonNull(queryType, "queryType must not be null");
            requireNonNull(attribute, "attribute must not be null");
            requireNonNull(resource, "resource must not be null");
            return new Query(queryType, attribute, resource);
        }
    }

    public List<Map<String, Object>> run(Resource resource) throws IOException, InterruptedException {
        if (this.resource.equalsIgnoreCase(resource.getName())) {
            List<Map<String, Object>> result = resource.call(queryType);
            if (!this.attribute.equals("*")) {
                result = result.stream()
                        .map(e -> Map.of(this.attribute, e.get(this.attribute)))
                        .toList();
            }
            return result;
        }
        return List.of();
    }
}
