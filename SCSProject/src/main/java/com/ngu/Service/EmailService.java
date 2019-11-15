package com.ngu.Service;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    public void SendEmail(SimpleMailMessage email);
    
    public void sendSimpleMessage(MimeMessage email) throws MessagingException, IOException; 
}
