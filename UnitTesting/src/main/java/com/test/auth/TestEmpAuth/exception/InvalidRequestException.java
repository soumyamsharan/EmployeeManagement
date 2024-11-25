package com.test.auth.TestEmpAuth.exception;

public class InvalidRequestException extends RuntimeException{

    private String errorType;

    public InvalidRequestException(String message, String errorType)
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
