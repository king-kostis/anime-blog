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
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class FeedService {
    private static final Logger logger = LoggerFactory.getLogger(FeedService.class);
    private static final RestTemplate restTemplate = new RestTemplate();
    private final String file = "feeds/feeds.txt";
    private final Path filePath = Paths.get(file);
    private final List<Feed> feeds;

    @Value("${rss.feed.url}")
    private String feedUrlStr;

    public FeedService(List<Feed> feeds){
        this.feeds = new ArrayList<>();
    }

    public List<Feed> getFeeds(){
        return feeds;
    }

    public void storeFeeds() throws Exception {
        URL feedUrl = new URL(feedUrlStr);
        String feedsContent = restTemplate.getForObject(feedUrlStr, String.class);
        try {
            logger.info("Checking if {} exists",filePath);
            Files.createDirectories(filePath.getParent());
            logger.info("Storing feeds");
            Files.writeString(filePath, feedsContent, StandardCharsets.UTF_8, StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
            logger.info("Feeds stored successfully \n {}", feedsContent);

        } catch (Exception e){
            logger.error("Error. Could not store feeds: {}", e.getMessage());
        }
    }

    public void loadFeeds(){
        try {
            String content = Files.readString(filePath, StandardCharsets.UTF_8); //reads contents from file

            logger.info("Instantiating document builder factory");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            logger.info("Parsing text to xml content");
            // Parse from string by wrapping it in InputSource
            InputSource inputSource = new InputSource(new StringReader(content));
            Document doc = builder.parse(inputSource);

            //Normalizes xml content
            doc.getDocumentElement().normalize();

            logger.info("Assigning item fields from xml file");
            logger.info("Creating feed objects");
            //iterates through feed items in xml file
            NodeList items = doc.getElementsByTagName("item"); // typical for RSS
            for (int i = 0; i < items.getLength(); i++) {
                Node item = items.item(i);
                if (item.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) item;
                    String title = element.getElementsByTagName("title").item(0).getTextContent();
                    String link = element.getElementsByTagName("link").item(0).getTextContent();
                    String description = element.getElementsByTagName("description").item(0).getTextContent();
                    String pubDate = element.getElementsByTagName("pubDate").item(0).getTextContent();

                    Feed feed = new Feed(title, description, pubDate, link);
                    feeds.add(feed);
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e){
            logger.error("Error reading file: {} \n {}",e.getMessage(), e.getStackTrace());
        }
    }
}