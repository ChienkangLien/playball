package com.tibame.tga105.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    
    @Autowired
    private JavaMailSenderImpl mailSender;

    public void sendEmail(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("yourGoogleEmail");
        message.setTo(to);
        message.setSubject("候補上囉");
        message.setText("務必出席!!!  若無法出席請再取消預約");
        mailSender.setPassword("yourPassword");
        mailSender.send(message);
    }
}
