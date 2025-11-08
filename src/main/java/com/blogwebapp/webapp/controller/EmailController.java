package com.blogwebapp.webapp.controller;

import com.blogwebapp.webapp.model.EmailDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/email")
public class EmailController {
    @PostMapping("/sendEmail")
    public String sendEmail(@Valid @ModelAttribute("email") EmailDto email){
        return "redirect:/posts";
    }
}
