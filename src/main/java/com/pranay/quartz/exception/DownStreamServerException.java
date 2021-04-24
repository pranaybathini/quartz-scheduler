package com.pranay.quartz.exception;

public class DownStreamServerException extends RuntimeException{

    public static final long serialVersionUID = 2672582268054381244L;

    public DownStreamServerException() {
        super();
    }

    public DownStreamServerException(String message) {
        super(message);
    }

    public DownStreamServerException(String message, Exception exception) {
        super(message, exception);
    }
}
