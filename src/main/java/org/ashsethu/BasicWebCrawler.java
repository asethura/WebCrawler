package org.ashsethu;

import org.ashsethu.controller.CrawlerController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.io.IOException;

@SpringBootApplication(scanBasePackages = "org.ashsethu")
public class BasicWebCrawler {

    public static void main(String[] args) throws IOException {
       //new CrawlerController().startCrawling("https://asethura.wixsite.com/techfortech");
        SpringApplication.run(BasicWebCrawler.class, args);
    }
}
