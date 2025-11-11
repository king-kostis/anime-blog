package com.blogwebapp.webapp.controller;

import com.blogwebapp.webapp.model.EmailDto;
import com.blogwebapp.webapp.service.EmailService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Controller
@RequestMapping("/blog")
public class EmailController {
    private final EmailService emailService;
    private static final Logger logger = LoggerFactory.getLogger(EmailController.class);

    public EmailController(EmailService emailService){
        this.emailService = emailService;
    }

    @PostMapping("/sendEmail")
    public String sendEmail(@Valid @ModelAttribute("email") EmailDto email){
        try {
            emailService.sendMail(email);
        } catch (Exception e){
            logger.info("Error: " + e.getMessage() + "\n"+ Arrays.toString(e.getStackTrace()));
            return "redirect:/blog/error";
        }
        return "redirect:/blog/contact";
    }

    @GetMapping("/error")
    public String error(){
        return "error";
    }
}
