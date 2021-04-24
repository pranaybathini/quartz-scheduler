package com.pranay.quartz.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pranay.quartz.models.response.Response;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Data
@Entity
@ToString
@Table(name = "mail_schedule")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MailSchedule implements Serializable {

    private static final long serialVersionUID = 6321323265487281667L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Integer scheduleId;

    @Column(name = "username")
    private String username;

    @Column(name = "to_email")
    private String toEmail;

    @Column(name = "schedule_datetime")
    private String scheduleDateTime;

    @Column(name = "schedule_zone_id")
    private String scheduleZoneId;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    public Response toResponse() {
        Response response = new Response();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        response.setScheduleId(this.scheduleId);
        response.setUsername(this.username);
        response.setToEmail(this.toEmail);
        response.setScheduledTime(LocalDateTime.parse(this.scheduleDateTime, formatter));
        response.setZoneId(ZoneId.of(this.scheduleZoneId));
        return response;
    }
}
