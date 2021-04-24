package com.pranay.quartz.models.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
public class Response {

    private Integer scheduleId;

    private String username;

    private String toEmail;

    private LocalDateTime scheduledTime;

    private ZoneId zoneId;

}
