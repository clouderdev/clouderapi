package com.clouder.clouderapi.service;

import javax.mail.MessagingException;

@FunctionalInterface
public interface EmailService {

    void sendEmail(String to, String cc, String subject, String body) throws MessagingException;

}
