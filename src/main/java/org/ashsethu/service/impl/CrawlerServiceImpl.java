package org.ashsethu.service.impl;

import org.ashsethu.config.Config;
import org.ashsethu.config.SessionConfigFactory;
import org.ashsethu.constants.Constants;
import org.ashsethu.repository.PageRepository;
import org.ashsethu.service.CrawlerService;
import org.ashsethu.service.OutputService;
import org.ashsethu.utils.DomainUtility;
import org.ashsethu.utils.HtmlParser;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CrawlerServiceImpl implements CrawlerService {

    protected static final Logger logger = LoggerFactory.getLogger(CrawlerServiceImpl.class);

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private OutputService outputService;


    @Autowired
    Config cfg ;

    public void crawlTheWeb() throws IOException {

        String url = null;
        String key = null;
        String[] arrUrls = null;
        int arrSize;


        //Get all config values
        int maxPages = cfg.getMaxPages();
        int depth = cfg.getMaxDepth();
        int threadCount = cfg.getMaxThread();
        String startingURL = cfg.getStartingUrl();

        //if starting url does not have protocol, add the same
        startingURL = !startingURL.startsWith("http")?"http://"+startingURL:startingURL;

        String baseDomain = DomainUtility.extractBaseDomain(startingURL);
        SessionConfigFactory.getSessionConfig().setBaseDomain(baseDomain);
        boolean allowExternalCrawl = cfg.isAllowExternal();

        System.out.println(" maxPages " + maxPages + " depth "+ depth + " allowExternalCrawl " + allowExternalCrawl);

        pageRepository.initiateCrawlList(startingURL);


        while (pageRepository.getCrawlListCount() > 0) {

            key = pageRepository.popFromCrawList();
            logger.debug(key);


            arrUrls = key.split(Constants.KEY_SEPARATOR);
            arrSize = arrUrls.length;
            url = arrUrls[arrSize-1];

            if(arrSize <= depth){
                //Get Links
                Elements elements = HtmlParser.getLinks(url);

                String baseUrl = DomainUtility.stripQueryString(url);
                logger.debug("base url " + baseUrl);

                if (elements != null){
                    for (Element element: elements){

                        String childUrl = element.attr("href");

                        String pageUrl = DomainUtility.validateAndFormURL(childUrl, baseUrl);

                        if (pageUrl != null){
                            pageRepository.addToPageKeyList(key, pageUrl);

                            //Don't add if max page is reached
                            if (pageRepository.getPageListCount() < maxPages){
                                //check if external crawl is allowed. If not and this is an external link, do not add to crawl list
                                if (allowExternalCrawl && DomainUtility.checkBaseDomain(pageUrl, baseDomain))
                                {
                                    pageRepository.addToBeCrawledList(key, pageUrl);
                                }

                            }
                        }

                    }
                }

                Elements images = HtmlParser.getImages(url);
                if (images != null){
                    for (Element image: images){
                        String childUrl = image.attr("src");
                        String imageUrl = DomainUtility.validateAndFormURL(childUrl, baseUrl);
                        if (imageUrl != null) {
                            pageRepository.addToImageKeyList(key, imageUrl);
                        }
                    }
                }

            }

        }

        outputService.createOutput(pageRepository.getAllPages(), pageRepository.getAllImages());
    }


}
