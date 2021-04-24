package com.pranay.quartz.service;

import com.pranay.quartz.models.request.Request;
import com.pranay.quartz.models.response.Response;

import java.util.List;

public interface MailScheduleService {

    public String createSchedule(Request request);

    public Response getSchedule(int id, String username);

    public List<Response> getSchedules(String username, int page, int size);

    public String updateSchedule( Request request);

    public String deleteSchedule(int id, String username);

}
