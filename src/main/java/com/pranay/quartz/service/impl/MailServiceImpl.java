package com.pranay.quartz.service.impl;

import com.pranay.quartz.exception.InternalServerException;
import com.pranay.quartz.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Component
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(String fromEmail, String toEmail, String subject, String message) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.toString());
            messageHelper.setSubject(subject);
            messageHelper.setText(message, true);
            messageHelper.setFrom(fromEmail);
            messageHelper.setTo(toEmail);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException ex) {
            throw new InternalServerException("Error Sending mail");
        }
    }
}
