package com.ngu.ServiceImpl;

import com.ngu.Model.Mail;
import com.ngu.Service.EmailService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Service("emailService")
public class EmailServiceImpl implements EmailService
{
    @Autowired
    private JavaMailSender javaMailSender;

    @Async

    public void SendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }
    
    

    public void sendSimpleMessage(MimeMessage email) throws MessagingException, IOException {
    	javaMailSender.send(email);
    }
    
}
