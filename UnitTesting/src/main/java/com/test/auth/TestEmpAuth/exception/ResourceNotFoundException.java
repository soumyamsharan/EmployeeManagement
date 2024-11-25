package com.test.auth.TestEmpAuth.exception;

public class ResourceNotFoundException extends RuntimeException{

    private String errorType;

    public ResourceNotFoundException(String message, String errorType)
    {
        super(message);
        this.errorType = errorType;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }
}
