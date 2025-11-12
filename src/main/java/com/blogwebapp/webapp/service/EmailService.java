package com.blogwebapp.webapp.service;

import com.blogwebapp.webapp.model.EmailDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderAddress;

    @Value("${app.mail.receiver}")
    private String receiverAddress;

    public EmailService(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    public void sendMail(EmailDto emailDto) throws Exception {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        logger.info("Setting email sender address " + senderAddress);
        simpleMailMessage.setFrom(senderAddress);

        logger.info("Setting email subject " + emailDto.getSubject());
        simpleMailMessage.setSubject(emailDto.getSubject());

        logger.info("Setting email body " + emailDto);
        simpleMailMessage.setText(emailDto.toString());
        logger.info("Setting email recipient address " + receiverAddress);
        simpleMailMessage.setTo(receiverAddress);

        logger.info("Sending email to Gmail Client");
        mailSender.send(simpleMailMessage);

        logger.info("Email sent from {} to {} ",senderAddress, receiverAddress);
        emailDto.setIsSent(true);
    }
}
