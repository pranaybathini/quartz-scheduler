package com.pranay.quartz.exception;

public class InternalServerException extends MailScheduleException {
    public static final long serialVersionUID = -477698186540144137L;

    public InternalServerException(String message) {
        super(message);
    }

    public InternalServerException(String message, Exception exception) {
        super(message, exception);
    }
}
