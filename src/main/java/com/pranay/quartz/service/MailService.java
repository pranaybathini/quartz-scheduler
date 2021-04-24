package com.pranay.quartz.service;

public interface MailService {

    public void sendMail(String fromEmail, String toEmail, String subject, String message);

}
