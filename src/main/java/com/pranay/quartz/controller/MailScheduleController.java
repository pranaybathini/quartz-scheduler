package com.pranay.quartz.controller;

import com.pranay.quartz.models.request.Request;
import com.pranay.quartz.models.response.ApiResponse;
import com.pranay.quartz.models.response.Response;
import com.pranay.quartz.service.MailScheduleService;
import com.pranay.quartz.utils.AppUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("quartz/mail/")
public class MailScheduleController {

    @Autowired
    private MailScheduleService mailScheduleService;

    @PostMapping("create")
    public ApiResponse createSchedule(@RequestBody Request request) {
        AppUtils.validateAndThrow(request);
        String scheduleId = mailScheduleService.createSchedule(request);
        return new ApiResponse(String.format("SCHEDULE CREATED WITH ID %s", scheduleId), HttpStatus.CREATED.value(), true);
    }

    @GetMapping("list")
    public ApiResponse getSchedules(@RequestParam(value = "username") String username,
                                    @RequestParam(defaultValue = "0", required = false) int page,
                                    @RequestParam(defaultValue = "1000", required = false) int size) {
        List<Response> schedules = mailScheduleService.getSchedules(username, page, size);
        return new ApiResponse(schedules, HttpStatus.OK.value(), true);
    }

    @GetMapping("list/{id}")
    public ApiResponse getSchedule(@RequestParam(value = "id") int id,
                                   @RequestParam(value = "username") String username) {
        Response schedule = mailScheduleService.getSchedule(id, username);
        return new ApiResponse(schedule, HttpStatus.OK.value(), true);
    }

    @PostMapping("update/{id}")
    public ApiResponse updateSchedule(@RequestBody Request request) {
        AppUtils.validateAndThrow(request);
        String scheduleId = mailScheduleService.updateSchedule(request);
        return new ApiResponse(String.format("SCHEDULE WITH ID %s UPDATED", scheduleId), HttpStatus.OK.value(), true);
    }

    @DeleteMapping("delete/{id}")
    public ApiResponse deleteSchedule(@RequestParam(value = "id") Integer id,
                                      @RequestParam(value = "username") String username) {
        String scheduleId = mailScheduleService.deleteSchedule(id, username);
        return new ApiResponse(String.format("SCHEDULE WITH ID %s DELETED", scheduleId), HttpStatus.OK.value(), true);
    }
}
