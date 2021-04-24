package com.pranay.quartz.utils;

public class Constants {

    private Constants() {
    }

    public static class QuartzScheduler {
        private QuartzScheduler() {
        }

        public static final String JOB_DETAIL_GROUP_ID = "QUARTZ_SCHEDULER_JOB_GROUP";
        public static final String JOB_DETAIL_DESCRIPTION = "QUARTZ_SCHEDULER_JOB_DESCRIPTION";
        public static final String TRIGGER_GROUP_ID = "QUARTZ_SCHEDULER_TRIGGER_GROUP";
        public static final String TRIGGER_DESCRIPTION = "QUARTZ_SCHEDULER_TRIGGER_DESCRIPTION";
    }

    public static class MailScheduleJob {
        private MailScheduleJob() {
        }

        public static final String TO_MAIL = "TO_MAIL";
        public static final String SUBJECT = "SUBJECT";
        public static final String MESSAGE = "MESSAGE";
        public static final String SCHEDULE_ID = "SCHEDULE_ID";
    }

}
