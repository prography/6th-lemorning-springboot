package com.example.demo.exception;

public class NotEnoughPointException extends RuntimeException{
    public NotEnoughPointException() {
        super();
    }

    public NotEnoughPointException(String message) {
        super(message);
    }

    public NotEnoughPointException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughPointException(Throwable cause) {
        super(cause);
    }

    protected NotEnoughPointException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
