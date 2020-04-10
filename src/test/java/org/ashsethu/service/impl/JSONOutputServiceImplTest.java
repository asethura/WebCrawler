package org.ashsethu.service.impl;

import org.ashsethu.config.Config;
import org.ashsethu.repository.PageRepository;
import org.ashsethu.service.CrawlerService;
import org.ashsethu.service.OutputService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class JSONOutputServiceImplTest {

    @Autowired
    OutputService outputService;

    @Autowired
    Config cfg;

    @Autowired
    CrawlerService csil;

    @Test
    void createOutput() throws IOException {

        cfg.setAllowExternal(true);
        cfg.setMaxPages(10);
        cfg.setMaxDepth(1);
        Properties properties = new Properties();

        properties.setProperty("crawler.output", "crawlMap.dat");

        PageRepository pageRepository = csil.crawlTheWeb("http://wiprodigital.com", "wiprodigital.com");

        outputService.createOutput(pageRepository.getAllPages(), pageRepository.getAllImages(), "wiprodigital.com");

        File file = new File("crawlMap.dat");

        BufferedReader br = new BufferedReader(new FileReader(file));
        assertTrue (br.lines().filter(r -> r.contains("Internal Links:")).count()> 0);

        br = new BufferedReader(new FileReader(file)); //reset buffer
        assertTrue (br.lines().filter(r -> r.contains("External Links:")).count()> 0);

        br = new BufferedReader(new FileReader(file)); //reset buffer
        assertTrue (br.lines().filter(r -> r.contains("Internal Images:")).count()> 0);

        br = new BufferedReader(new FileReader(file)); //reset bufferr
        assertTrue (br.lines().filter(r -> r.contains("External Images:")).count()> 0);

    }
}
