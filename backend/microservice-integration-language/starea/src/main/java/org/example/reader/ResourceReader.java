package org.example.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.App;
import org.example.resource.Resource;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ResourceReader {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static Resource read(String filename) throws IOException, URISyntaxException {
        URI uri = App.class.getClassLoader().getResource(filename).toURI();
        String resourceStr = Files.readString(Paths.get(uri), Charset.defaultCharset());
        return mapper.readValue(resourceStr, Resource.class);
    }
}
