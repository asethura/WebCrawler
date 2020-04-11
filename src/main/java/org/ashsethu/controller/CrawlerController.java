package org.ashsethu.controller;

import org.ashsethu.config.Config;
import org.ashsethu.repository.PageRepository;
import org.ashsethu.service.CrawlerService;
import org.ashsethu.service.OutputService;
import org.ashsethu.utils.DomainUtility;
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

    @Autowired
    private OutputService outputService;

    @Autowired
    DomainUtility domainUtility;

    @GetMapping(value = "/startCrawling")
    public String startCrawling(@RequestParam(value = "startingUrl") String startingUrl) throws IOException {

        String baseDomain = domainUtility.extractBaseDomain(startingUrl);
        PageRepository pageRepository = csil.crawlTheWeb(startingUrl, baseDomain);

        //Wrrite output
        outputService.createOutput(pageRepository.getAllPages(), pageRepository.getAllImages(), baseDomain);

        return "Successfully Crawled. Output is available in the file " + cfg.getCrawlerOutput();
    }
}
