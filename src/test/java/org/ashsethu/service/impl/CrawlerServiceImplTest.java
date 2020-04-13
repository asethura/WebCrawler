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
import org.mockito.Spy;
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
    void crawlTheWeb_Page_Count_Test() throws IOException {

        when(mcfg.isAllowExternal()).thenReturn(true);
        when(mcfg.getMaxDepth()).thenReturn(1);
        when(mcfg.getMaxPages()).thenReturn(10);

        when(mhtmlParser.getLinks(Mockito.any())).thenReturn(createElementList());

        PageRepository pageRepository = csil.crawlTheWeb("http://wiprodigital.com", "wiprodigital.com");

        assertNotNull(pageRepository);

        assertEquals(3, pageRepository.getPageListCount()); //3 pages should be returned as that is the list provided by mock

    }

    private Elements createElementList(){
        Element el1 = new Element("a").attr("href", "http://linkedin.com");
        Element el2 = new Element("a").attr("href", "http://twitter.com");
        Element el3 = new Element("a").attr("href", "http://google.com");
        Elements els = new Elements();
        els.add(el1);
        els.add(el2);
        els.add(el3);
        return els;
    }

    @Test
    void crawlTheWeb_MaxDepth_Test() throws IOException {
        when(mcfg.isAllowExternal()).thenReturn(true);
        when(mcfg.getMaxDepth()).thenReturn(1);
        when(mcfg.getMaxPages()).thenReturn(10);
        when(mhtmlParser.getLinks(Mockito.any())).thenReturn(createElementList());
        PageRepository pageRepository = csil.crawlTheWeb("http://wiprodigital.com", "wiprodigital.com");
        verify(mhtmlParser, times(1)).getLinks(Mockito.any()); //get links should be called only once as depth has been set to 1
    }

    @Test
    void crawlTheWeb_MaxCount_Test() throws IOException {
        int maxPageCount = 2;
        when(mcfg.isAllowExternal()).thenReturn(true);
        when(mcfg.getMaxDepth()).thenReturn(1);
        when(mcfg.getMaxPages()).thenReturn(maxPageCount);
        when(mhtmlParser.getLinks(Mockito.any())).thenReturn(createElementList());
        PageRepository pageRepository = csil.crawlTheWeb("http://wiprodigital.com", "wiprodigital.com");
        ArrayList<String> pages = pageRepository.getAllPages();
        assertEquals(maxPageCount, pages.size()); //returned pages count should be equal to the max count set initially
    }


    @Test
    void crawlTheWeb_NotAllowExternal_Test() throws IOException {
        when(mcfg.isAllowExternal()).thenReturn(false);
        when(mcfg.getMaxDepth()).thenReturn(2);
        when(mcfg.getMaxPages()).thenReturn(10);
        when(mhtmlParser.getLinks(Mockito.any())).thenReturn(createElementList());

        PageRepository pageRepository = csil.crawlTheWeb("http://wiprodigital.com", "wiprodigital.com");

        verify(mhtmlParser, times(1)).getLinks("http://wiprodigital.com"); //There should be only 1 url that is starting page because all returned ones are externral
        }


}