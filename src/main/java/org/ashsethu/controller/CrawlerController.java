package org.ashsethu.controller;

import org.ashsethu.config.Config;
import org.ashsethu.service.CrawlerService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value = "/")
public class CrawlerController {

    @Autowired
    CrawlerService csil;

    @Autowired
    Config cfg ;

    @GetMapping(value = "/startCrawling")
    public String startCrawling(@RequestParam(value = "startingUrl") String startingUrl) throws IOException {
        csil.crawlTheWeb(startingUrl);
        return "Successfully Crawled. Output is available in the file " + cfg.getCrawlerOutput();
    }
}
