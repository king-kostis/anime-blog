package com.blogwebapp.webapp.controller;

import com.blogwebapp.webapp.model.EmailDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/blog")
public class HomeController {
    @GetMapping("/posts")
    public String getPosts(){
        return "home";
    }

    @GetMapping("/about")
    public String getAbout(){
        return "about";
    }

    @GetMapping("/contact")
    public String getContact(Model model){
        model.addAttribute("email", new EmailDto());
        return "contact";
    }
}
