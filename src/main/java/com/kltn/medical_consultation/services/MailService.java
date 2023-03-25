package com.kltn.medical_consultation.services;

import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


@Service
@NoArgsConstructor
public class MailService {

    private static final Logger LOGGER = LogManager.getLogger(MailService.class);

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private MailProperties properties;

    private ThreadPoolExecutor pool = new ThreadPoolExecutor(2,
            Integer.MAX_VALUE, 10, TimeUnit.MINUTES, new LinkedBlockingQueue<>());

    public void sendSimpleMessage(String to, String subject, String body, boolean async) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false);
        helper.setFrom(properties.getUsername());
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);
        if (async) {
            this.addSendTask(message);
        } else {
            emailSender.send(message);
        }
    }

    private void addSendTask(final MimeMessage mimeMessage) {
        try {
            pool.execute(() -> {
                emailSender.send(mimeMessage);
                LOGGER.debug("Send successfully !");
            });
        } catch (Exception e) {
            LOGGER.error("Exception when sen email !", e);
        }
    }

}
