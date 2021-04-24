package com.pranay.quartz.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MailScheduleException extends RuntimeException {

    public static final long serialVersionUID = 6958844950242578857L;

    private final String message;

    public MailScheduleException(String message) {
        super(message);
        this.message = message;
    }

    public MailScheduleException(String message, Exception e) {
        super(message, e);
        this.message = message;
    }

    public MailScheduleException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}
