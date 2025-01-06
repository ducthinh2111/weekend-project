package org.example.exception;

public class VariableNotFoundException extends RuntimeException {

    public VariableNotFoundException(String variable) {
        super("Variable " + variable + " not found");
    }
}
