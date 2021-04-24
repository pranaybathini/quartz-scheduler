package com.pranay.quartz.models.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pranay.quartz.models.entity.MailSchedule;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Request {

    private Integer scheduleId;

    @NotNull(message = "username cannot be null")
    private String username;

    @Email(message = "Provided email format is not valid")
    @NotNull(message = "toEmail cannot be null")
    private String toEmail;

    @NotNull(message = "subject cannot be null")
    private String subject;

    @NotNull(message = "message cannot be null")
    private String message;

    @NotNull(message = "scheduledTime cannot be null")
    private LocalDateTime scheduledTime;

    @NotNull(message = "zoneId cannot be null")
    private ZoneId zoneId;

    public MailSchedule toMailSchedule() {
        MailSchedule mailSchedule = new MailSchedule();
        mailSchedule.setDeleted(false);
        mailSchedule.setScheduleId(this.scheduleId);
        mailSchedule.setUsername(this.username);
        mailSchedule.setToEmail(this.toEmail);
        mailSchedule.setScheduleDateTime(this.scheduledTime.toString());
        mailSchedule.setScheduleZoneId(this.zoneId.toString());
        return mailSchedule;
    }

}
