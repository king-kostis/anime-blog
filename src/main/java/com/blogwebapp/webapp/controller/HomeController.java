package com.blogwebapp.webapp.controller;

import com.blogwebapp.webapp.model.EmailDto;
import com.blogwebapp.webapp.service.FeedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/blog")
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    private FeedService feedService;

    public HomeController(FeedService feedService){
        this.feedService = feedService;
    }

    @GetMapping("/feed")
    public String getPosts(Model model){
        if(feedService.getFeeds().isEmpty()) {
            try {
                logger.info("Storing feeds");
                feedService.storeFeeds();
                logger.info("Loading feeds");
                feedService.loadFeeds();
            } catch (Exception e) {
                logger.error("Error loading feed: {}", e.getMessage());
            }
        }
        model.addAttribute("feeds", feedService.getFeeds());
        return "feed";
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
