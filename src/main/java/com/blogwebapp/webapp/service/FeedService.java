package com.blogwebapp.webapp.service;

import com.blogwebapp.webapp.model.Feed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class FeedService {
    private static final Logger logger = LoggerFactory.getLogger(FeedService.class);
    private static final RestTemplate restTemplate = new RestTemplate();
    private final String file = "feeds/feeds.txt";
    private final Path filePath = Paths.get(file);
    private final List<Feed> feeds;
    private String imageUrl;
    @Value("${rss.feed.url}")
    private String feedUrlStr;
    private static String imgUrl;

    public FeedService(List<Feed> feeds){
        this.feeds = new ArrayList<>();
    }

    public List<Feed> getFeeds(){
        return feeds;
    }

    //Stores rss feeds for persistence
    public void storeFeeds() throws Exception {
        URL feedUrl = new URL(feedUrlStr);
        String feedsContent = restTemplate.getForObject(feedUrlStr, String.class);// Parses xml code to string text

        try {
            logger.info("Checking if {} exists",filePath);
            Files.createDirectories(filePath.getParent());
            logger.info("Fetching and storing feeds");

            //Overwrites data in the file after every save
            Files.writeString(filePath, feedsContent, StandardCharsets.UTF_8, StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
            logger.info("Feeds stored successfully");

        } catch (Exception e){
            logger.error("Error. Could not store feeds: {}", e.getMessage());
        }
    }

    public void loadFeeds(){
        try {
            //Loads feed from feeds file
            String content = Files.readString(filePath, StandardCharsets.UTF_8); //reads contents from file

            logger.debug("Instantiating document builder factory");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            logger.debug("Parsing text to xml content");
            InputSource inputSource = new InputSource(new StringReader(content));
            Document doc = builder.parse(inputSource);
            //Normalizes xml content
            doc.getDocumentElement().normalize();

            logger.debug("Assigning item fields from xml file");
            logger.debug("Creating feed objects");
            //iterates through feed items in xml file
            NodeList items = doc.getElementsByTagName("item"); // typical for RSS
            for (int i = 0; i < items.getLength(); i++) {
                Node item = items.item(i);
                if (item.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) item;
                    String title = element.getElementsByTagName("title").item(0).getTextContent();
                    String link = element.getElementsByTagName("link").item(0).getTextContent();
                    String pubDate = element.getElementsByTagName("pubDate").item(0).getTextContent();
                    String description = element.getElementsByTagName("description").item(0).getTextContent();

                    //This is to extract the image url from the description field using regex matcher
                    Pattern imgPattern = Pattern.compile("<img[^>]+src=[\"']([^\"']+)[\"']");
                    Matcher matcher = imgPattern.matcher(description);

                    //Checks for url and assigns it to imageUrl if found
                    if(matcher.find()) {
                        imgUrl = matcher.group(1);
                        if (!isImageAvailable(imgUrl)) {
                            // Removes the image tag completely if url is not found or active
                            description = description.replaceAll("<img[^>]+src=[\"']" + Pattern.quote(imgUrl) + "[\"'][^>]*>", "");
                        }
                    }

                    Feed feed = new Feed(title, description, pubDate, link);
                    feeds.add(feed);
                }
            }
            logger.info("Feeds loaded successfully");

        } catch (ParserConfigurationException | SAXException | IOException e){
            logger.error("Error reading file: {} \n {}",e.getMessage(), e.getStackTrace());
        }
    }

    //Checks if the image url is active
    private static boolean isImageAvailable(String urlStr) {
        try {
            URL url = new URL(urlStr);
            logger.info("Establishing connection to image URL: {}", imgUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("HEAD");
            logger.info("Connection established. Request has been sent");
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            int responseCode = conn.getResponseCode();
            logger.info("Response code has been retrieved: {}", responseCode);
            return (responseCode >= 200 && responseCode < 400);
        } catch (Exception e) {
            logger.error("Error: {}",e.getMessage());
            return false;
        }
    }
}