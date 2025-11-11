package com.blogwebapp.webapp.controller;

import com.blogwebapp.webapp.model.EmailDto;
import com.blogwebapp.webapp.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/blog")
public class EmailController {
    private EmailService emailService;

    public EmailController(EmailService emailService){
        this.emailService = emailService;
    }

    @PostMapping("/sendEmail")
    public String sendEmail(@Valid @ModelAttribute("email") EmailDto email){
        emailService.sendMail(email);
        return "redirect:/blog/contact";
    }
}
