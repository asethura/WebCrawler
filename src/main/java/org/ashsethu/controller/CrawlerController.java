package org.ashsethu.controller;

import org.ashsethu.service.CrawlerService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value = "/")
public class CrawlerController {

    @Autowired
    CrawlerService csil;

    @GetMapping(value = "/startCrawling")
    public void startCrawling() throws IOException {
        csil.crawlTheWeb();
    }
}
