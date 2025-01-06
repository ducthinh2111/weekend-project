package org.example.exception;

public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String resourceName) {
        super("Resource not found: " + resourceName);
    }
}
