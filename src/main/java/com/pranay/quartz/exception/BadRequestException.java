package com.pranay.quartz.exception;

public class BadRequestException extends MailScheduleException {

    public static final long serialVersionUID = -3673718945533722277L;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Exception exception) {
        super(message, exception);
    }

}
