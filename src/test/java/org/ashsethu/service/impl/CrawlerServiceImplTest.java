package org.ashsethu.service.impl;


import org.ashsethu.config.Config;
import org.ashsethu.constants.Constants;
import org.ashsethu.repository.PageRepository;
import org.ashsethu.service.CrawlerService;
import org.ashsethu.utils.DomainUtility;
import org.ashsethu.utils.HtmlParser;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.*;
//import static org.powermock.api.mockito.PowerMockito.*;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class CrawlerServiceImplTest{

    @Autowired
    CrawlerServiceImpl csil;

    @Autowired
    Config cfg;

    @MockBean
    Config mcfg;


    @MockBean
    HtmlParser mhtmlParser;


    @Test
    void crawlTheWeb_Max_Depth_Test() throws IOException {

        when(mcfg.isAllowExternal()).thenReturn(true);
        when(mcfg.getMaxDepth()).thenReturn(1);
        when(mcfg.getMaxPages()).thenReturn(10);

        when(mhtmlParser.getLinks(Mockito.any())).thenReturn(createElementList());

        PageRepository pageRepository = csil.crawlTheWeb("http://wiprodigital.com", "wiprodigital.com");

        assertNotNull(pageRepository);

        assertEquals(2, pageRepository.getPageListCount());

    }

    private Elements createElementList(){
        Element el1 = new Element("html").attr("href", "http://linkedin.com");
        Element el2 = new Element("html").attr("href", "http://twitter.com");
        Elements els = new Elements();
        els.add(el1);
        els.add(el2);
        return els;
    }

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