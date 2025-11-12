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
            logger.error("Error: " + e.getMessage());
            return "redirect:/blog/contact?error";
        }
        return "redirect:/blog/contact?success";
    }

    @GetMapping("/error")
    public String error(){
        return "error";
    }
}
