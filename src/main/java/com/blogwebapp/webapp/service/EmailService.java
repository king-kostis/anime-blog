package com.blogwebapp.webapp.service;
import com.blogwebapp.webapp.model.EmailDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private JavaMailSender mailSender;

    @Value("spring.mail.username")
    private String toMail;

    public EmailService(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }



    public void sendMail(EmailDto emailDto){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(emailDto.getEmailAddress());
        simpleMailMessage.setSubject(emailDto.getSubject());
        simpleMailMessage.setText(emailDto.getMessage());
        simpleMailMessage.setTo(toMail);

        mailSender.send(simpleMailMessage);
    }
}
