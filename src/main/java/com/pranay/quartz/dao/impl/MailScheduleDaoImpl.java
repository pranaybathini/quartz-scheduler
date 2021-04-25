package com.pranay.quartz.dao.impl;

import com.pranay.quartz.dao.MailScheduleDao;
import com.pranay.quartz.dao.repository.MailScheduleRepository;
import com.pranay.quartz.exception.InternalServerException;
import com.pranay.quartz.jobs.MailScheduleJob;
import com.pranay.quartz.models.request.Request;
import com.pranay.quartz.models.entity.MailSchedule;
import com.pranay.quartz.models.response.Response;
import com.pranay.quartz.utils.AppUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;

import static com.pranay.quartz.utils.Constants.MailScheduleJob.*;
import static com.pranay.quartz.utils.Constants.QuartzScheduler.*;

@Component
public class MailScheduleDaoImpl implements MailScheduleDao {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private MailScheduleRepository mailScheduleRepository;

    @Override
    @Transactional
    public String createSchedule(Request request, ZonedDateTime zonedDateTime) {
        String scheduleId = saveSchedule(request);
        JobDetail jobDetail = getJobDetail(scheduleId, request);
        Trigger simpleTrigger = getSimpleTrigger(jobDetail, zonedDateTime);
        scheduleJob(jobDetail, simpleTrigger);
        return scheduleId;
    }

    public String saveSchedule(Request request) {
        try {
            MailSchedule save = mailScheduleRepository.save(request.toMailSchedule());
            return save.getScheduleId().toString();
        } catch (Exception e) {
            throw new InternalServerException("Unable to save schedule to DB");
        }
    }

    public JobDetail getJobDetail(String scheduleId, Request request) {
        Integer jobId = Integer.valueOf(scheduleId);
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(TO_MAIL, request.getToEmail());
        jobDataMap.put(SUBJECT, request.getSubject());
        jobDataMap.put(MESSAGE, request.getMessage());
        jobDataMap.put(SCHEDULE_ID, scheduleId);

        return JobBuilder.newJob(MailScheduleJob.class)
                .withIdentity(String.valueOf(jobId), JOB_DETAIL_GROUP_ID)
                .withDescription(JOB_DETAIL_DESCRIPTION)
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    public Trigger getSimpleTrigger(JobDetail jobDetail, ZonedDateTime zonedDateTime) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), TRIGGER_GROUP_ID)
                .withDescription(TRIGGER_DESCRIPTION)
                .startAt(Date.from(zonedDateTime.toInstant()))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();
    }

    public Trigger getCronTrigger(JobDetail jobDetail, String cronExpression) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), TRIGGER_GROUP_ID)
                .withDescription(TRIGGER_DESCRIPTION)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression).
                        withMisfireHandlingInstructionFireAndProceed().inTimeZone(TimeZone.getTimeZone(ZoneOffset.UTC)))
                .build();
    }

    public void scheduleJob(JobDetail jobDetail, Trigger trigger) {
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException schedulerException) {
            throw new InternalServerException("Error creating the schedule");
        }
    }

    @Override
    public Response getSchedule(int scheduleId, String username) {
        Response response = null;
        try {
            Optional<MailSchedule> optional = mailScheduleRepository.findByScheduleIdAndIsDeletedFalse(scheduleId);
            if (optional.isPresent()) {
                response = optional.get().toResponse();
            }
        } catch (Exception e) {
            throw new InternalServerException("Error fetching the schedule");
        }
        return response;
    }

    @Override
    public List<Response> getSchedules(String username, int page, int size) {
        List<Response> responses = new ArrayList<>();
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<MailSchedule> schedules = mailScheduleRepository.findByUsernameAndIsDeletedFalse(username, pageable);
            List<MailSchedule> mailSchedules = schedules.getContent();
            responses = AppUtils.getResponseDtoListFrom(mailSchedules);
        } catch (Exception e) {
            throw new InternalServerException("Unable to fetch all schedules from DB");
        }
        return responses;
    }

    @Override
    @Transactional
    public void deleteSchedule(int scheduleId, String username) {
        deleteMailSchedule(scheduleId);
        deleteJobAndTrigger(scheduleId);
    }

    public void deleteMailSchedule(Integer scheduleId) {
        try {
            Optional<MailSchedule> optional = mailScheduleRepository.findById(scheduleId);
            if (optional.isPresent()) {
                MailSchedule mailSchedule = optional.get();
                mailSchedule.setDeleted(true);
                mailScheduleRepository.save(mailSchedule);
            }
        } catch (Exception e) {
            throw new InternalServerException("Error deleting schedule");
        }
    }

    public void deleteJobAndTrigger(Integer scheduleId) {
        try {
            scheduler.unscheduleJob(new TriggerKey(scheduleId.toString(), TRIGGER_GROUP_ID));
            scheduler.deleteJob(new JobKey(scheduleId.toString(), JOB_DETAIL_GROUP_ID));
        } catch (SchedulerException schedulerException) {
            throw new InternalServerException("Unable to delete the job from scheduler");
        }
    }

    @Override
    public boolean checkIfScheduleExists(String username, int id) {
        try {
            return mailScheduleRepository.existsByUsernameAndScheduleIdAndIsDeletedFalse(username, id);
        } catch (Exception e) {
            throw new InternalServerException("Error checking if schedule exists");
        }
    }

    @Override
    @Transactional
    public String updateSchedule(Request request, ZonedDateTime zonedDateTime) {
        updateMailSchedule(request);
        JobDetail jobDetail = updateJobDetail(request);
        updateTriggerDetails(request, jobDetail, zonedDateTime);
        return request.getScheduleId().toString();
    }

    public void updateMailSchedule(Request request) {
        try {
            Optional<MailSchedule> optional = mailScheduleRepository.findByScheduleIdAndIsDeletedFalse(request.getScheduleId());
            if (optional.isPresent()) {
                MailSchedule mailSchedule = optional.get();
                mailSchedule.setUsername(request.getUsername());
                mailSchedule.setToEmail(request.getToEmail());
                mailSchedule.setScheduleDateTime(request.getScheduledTime().toString());
                mailSchedule.setScheduleZoneId(request.getZoneId().toString());
                mailScheduleRepository.save(mailSchedule);
            }
        } catch (Exception e) {
            throw new InternalServerException("Unable to update the schedule in DB");
        }
    }

    public JobDetail updateJobDetail(Request request) {
        JobDetail jobDetail = null;
        try {
            if (request.getScheduleId() != null) {
                jobDetail = scheduler.getJobDetail(new JobKey(request.getScheduleId().toString(), JOB_DETAIL_GROUP_ID));
                jobDetail.getJobDataMap().put(TO_MAIL, request.getToEmail());
                jobDetail.getJobDataMap().put(SUBJECT, request.getSubject());
                jobDetail.getJobDataMap().put(MESSAGE, request.getMessage());
                scheduler.addJob(jobDetail, true);
            }
        } catch (SchedulerException schedulerException) {
            throw new InternalServerException("Unable to update the job data map");
        }
        return jobDetail;
    }

    public void updateTriggerDetails(Request request, JobDetail jobDetail, ZonedDateTime zonedDateTime) {
        Trigger newTrigger = getSimpleTrigger(jobDetail, zonedDateTime);
        try {
            scheduler.rescheduleJob(new TriggerKey(request.getScheduleId().toString(), TRIGGER_GROUP_ID), newTrigger);
        } catch (SchedulerException schedulerException) {
            throw new InternalServerException("Unable to update the trigger in DB");
        }
    }

}
