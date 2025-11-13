package com.blogwebapp.webapp.controller;

import com.blogwebapp.webapp.model.EmailDto;
import com.blogwebapp.webapp.service.FeedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.HttpURLConnection;
import java.net.URL;

@Controller
@RequestMapping("/blog")
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    private FeedService feedService;

    @Value("${rss.feed.url}")
    private String urlStr;

    public HomeController(FeedService feedService){
        this.feedService = feedService;
    }

    @GetMapping("/feed")
    public String getPosts(Model model){
        if(feedService.getFeeds().isEmpty()) {
            try {
                //Stores
                if (isRssFeedAvailable(urlStr)){
                    feedService.storeFeeds();
                    logger.info("Loading feeds");
                    feedService.loadFeeds();
                } else{
                    feedService.loadFeeds();
                }
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

    //Checks if the rss feed url is active
    private static boolean isRssFeedAvailable(String urlStr) {
        try {
            URL url = new URL(urlStr);
            logger.info("Establishing connection to rss feed");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("HEAD");
            logger.info("Connection established. Request has been sent");
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            int responseCode = conn.getResponseCode();
            logger.info("Response code has been retrieved: {}", responseCode);
            return (responseCode >= 200 && responseCode < 400);
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
            logger.info("Loading rss feed from local storage");
            return false;
        }
    }
}
