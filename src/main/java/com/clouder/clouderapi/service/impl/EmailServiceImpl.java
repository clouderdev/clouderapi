package com.clouder.clouderapi.service.impl;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.clouder.clouderapi.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${mail.smtp.host}")
    private String      host;

    @Value("${mail.smtp.socketFactory.port}")
    private String      socketFactoryPort;

    @Value("${mail.smtp.socketFactory.class}")
    private String      socketFactoryClass;

    @Value("${mail.smtp.auth}")
    private String      auth;

    @Value("${mail.smtp.port}")
    private String      port;

    @Value("${admin.email}")
    private String      adminEmail;

    @Value("${admin.password}")
    private String      adminPassword;

    private MimeMessage message;

    public EmailServiceImpl() {
        // Default constructor
    }

    @PostConstruct
    public void init() throws MessagingException {
        System.out.println(host);
        Properties props = System.getProperties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.socketFactory.port", socketFactoryPort);
        props.put("mail.smtp.socketFactory.class", socketFactoryClass);
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(adminEmail, adminPassword);
            }
        });
        message = new MimeMessage(session);
        message.setFrom(new InternetAddress(adminEmail));
    }

    @Override
    public void sendEmail(String to, String cc, String subject, String body) throws MessagingException {
        InternetAddress[] address = { new InternetAddress(to) };
        message.setRecipients(Message.RecipientType.TO, address);
        message.setSubject(subject);
        message.setContent(body, "text/html; charset=utf-8");
        Transport.send(message);
    }

}
