package com.pranay.quartz.service.impl;

import com.pranay.quartz.dao.MailScheduleDao;
import com.pranay.quartz.exception.BadRequestException;
import com.pranay.quartz.models.request.Request;
import com.pranay.quartz.models.response.Response;
import com.pranay.quartz.service.MailScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class MailScheduleServiceImpl implements MailScheduleService {

    @Autowired
    private MailScheduleDao mailScheduleDao;

    @Override
    public String createSchedule(Request request) {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(request.getScheduledTime(), request.getZoneId());
        if (zonedDateTime.isBefore(ZonedDateTime.now())) {
            throw new BadRequestException("Scheduled Time should be greater than current time");
        }
        return mailScheduleDao.createSchedule(request, zonedDateTime);
    }

    @Override
    public Response getSchedule(int id, String username) {
        Response response = mailScheduleDao.getSchedule(id, username);
        if (response == null) {
            throw new BadRequestException(String.format("No Active Schedule exists with id : %s", id));
        }
        return response;
    }

    @Override
    public List<Response> getSchedules(String username, int page, int size) {
        return mailScheduleDao.getSchedules(username, page, size);
    }

    @Override
    public String updateSchedule(Request request) {
        checkIfScheduleExists(request.getScheduleId(), request.getUsername());
        ZonedDateTime zonedDateTime = ZonedDateTime.of(request.getScheduledTime(), request.getZoneId());
        return mailScheduleDao.updateSchedule(request, zonedDateTime);
    }

    @Override
    public String deleteSchedule(int id, String username) {
        checkIfScheduleExists(id, username);
        mailScheduleDao.deleteSchedule(id, username);
        return String.valueOf(id);
    }

    private void checkIfScheduleExists(int id, String username) {
        boolean exists = mailScheduleDao.checkIfScheduleExists(username, id);
        if (!exists) {
            throw new BadRequestException(String.format("An active schedule with id %s does not exist", id));
        }
    }
}
