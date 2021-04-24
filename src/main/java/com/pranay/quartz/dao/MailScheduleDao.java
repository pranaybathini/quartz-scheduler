package com.pranay.quartz.dao;

import com.pranay.quartz.models.request.Request;
import com.pranay.quartz.models.response.Response;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;

@Component
public interface MailScheduleDao {

    public String createSchedule(Request request, ZonedDateTime zonedDateTime);

    public Response getSchedule(int id, String username);

    public List<Response> getSchedules(String username, int page, int size);


    public String updateSchedule(Request request, ZonedDateTime zonedDateTime);

    public void deleteSchedule(int id, String username);

    public boolean checkIfScheduleExists(String username, int id);

    public void deleteMailSchedule(Integer scheduleId);


}
