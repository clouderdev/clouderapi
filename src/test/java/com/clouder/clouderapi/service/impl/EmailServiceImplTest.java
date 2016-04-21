package com.clouder.clouderapi.service.impl;

import javax.mail.MessagingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.clouder.clouderapi.service.EmailService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class EmailServiceImplTest {

    @Autowired
    EmailService emailService;

    @Test
    public final void testSendEmail() throws MessagingException {

        String emailLink = "http://localhost:8080/clouderapi/verifyemail?username=shrinivas93&key=MTQ2MTI2ODMyMjMzNA==";

        emailService.sendEmail("ssshukla1993@gmail.com", null, "Email Verification",
                "Click on the following link to verify your email account<br>" + emailLink);
    }

}
