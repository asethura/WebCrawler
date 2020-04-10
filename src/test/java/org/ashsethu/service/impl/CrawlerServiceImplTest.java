package org.ashsethu.service.impl;


import org.ashsethu.config.Config;
import org.ashsethu.constants.Constants;
import org.ashsethu.repository.PageRepository;
import org.ashsethu.service.CrawlerService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class CrawlerServiceImplTest {

    @Autowired
    CrawlerService csil;

    @Autowired
    Config cfg;



    @Test
    void crawlTheWeb_MaxDepth_Test() throws IOException {

        cfg.setAllowExternal(true);
        cfg.setMaxDepth(1);
        PageRepository pageRepository = csil.crawlTheWeb("http://wiprodigital.com", "wiprodigital.com");
        ArrayList<String> pages = pageRepository.getAllPages();
        for (String record : pages) {

            String links[] = record.split(Constants.KEY_SEPARATOR);
            int actualDepth = links.length - 1; //reducing 1 because first part is base domain

            if (actualDepth > 1) {
                assert (false); //if length of array exceeds 1 then depth has exceeded 1. Fail the test
                return;
            }
        }
        assert (true);//if not asserted false, then assert true
    }

    @Test
    void crawlTheWeb_MaxCount_Test() throws IOException {
        cfg.setAllowExternal(true);
        cfg.setMaxPages(10);
        PageRepository pageRepository = csil.crawlTheWeb("http://wiprodigital.com", "wiprodigital.com");
        ArrayList<String> pages = pageRepository.getAllPages();

        if (pages.size() > 10) {
            assert (false); //if length of array exceeds 1 then depth has exceeded 1. Fail the test
            return;
        }

        assert (true);//if not asserted false, then assert true
    }


    @Test
    void crawlTheWeb_NotAllowExternal_Test() throws IOException {
        cfg.setAllowExternal(false);
        cfg.setMaxPages(1000);
        cfg.setMaxDepth(1);

        PageRepository pageRepository = csil.crawlTheWeb("https://asethura.wixsite.com/techfortech", "asethura.wixsite.com");
        ArrayList<String> pages = pageRepository.getAllPages();

        for (String record : pages) {

            String[] records = record.split(Constants.KEY_SEPARATOR);

            String lastButOneRecord = records[records.length - 2];

            if (!lastButOneRecord.contains("asethura.wixsite.com")){
                assert(false);
                return;
            }
        }
        assert (true);//if not asserted false, then assert true
    }


}