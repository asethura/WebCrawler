package org.ashsethu.service.impl;

import org.ashsethu.config.Config;

import org.ashsethu.constants.Constants;
import org.ashsethu.repository.PageRepository;
import org.ashsethu.service.CrawlerService;
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
    Config cfg;

    @Autowired
    HtmlParser htmlParser;

    @Autowired
    DomainUtility domainUtility;


    public PageRepository crawlTheWeb(String startingURL, String baseDomain) throws IOException {

        String url = null;
        String key = null;
        String[] arrUrls = null;
        int arrSize;


        //Get all config values
        int maxPages = cfg.getMaxPages();
        int depth = cfg.getMaxDepth();
        int threadCount = cfg.getMaxThread();
        boolean allowExternalCrawl = cfg.isAllowExternal();

        //if starting url does not have protocol, add the same
        startingURL = !startingURL.startsWith("http") ? "http://" + startingURL : startingURL;


        pageRepository.initiateCrawlList(startingURL);


        while (pageRepository.getCrawlListCount() > 0 ) {

            key = pageRepository.popFromCrawList();

            arrUrls = key.split(Constants.KEY_SEPARATOR);
            arrSize = arrUrls.length;
            url = arrUrls[arrSize - 1];


            logger.debug("depth " + depth);
            logger.debug(key);

            if (arrSize <= depth) {

                //Get Links
                Elements elements = htmlParser.getLinks(url);

                String baseUrl = domainUtility.stripQueryString(url);


                if (elements != null) {
                    for (Element element : elements) {

                        String childUrl = element.attr("href");

                        String pageUrl = domainUtility.validateAndFormURL(childUrl, baseUrl);

                        if (pageUrl != null && pageRepository.getPageListCount() < maxPages) {

                            pageRepository.addToPageKeyList(key, pageUrl);

                            //Don't add if max page is reached

                            //check if external crawl is allowed. If not and this is an external link, do not add to crawl list
                            if (allowExternalCrawl && domainUtility.checkBaseDomain(pageUrl, baseDomain)) {
                                pageRepository.addToBeCrawledList(key, pageUrl);
                            }
                        }

                    }
                }
                Elements images = htmlParser.getImages(url);
                if (images != null) {
                    for (Element image : images) {
                        String childUrl = image.attr("src");
                        String imageUrl = domainUtility.validateAndFormURL(childUrl, baseUrl);
                        if (imageUrl != null) {
                            pageRepository.addToImageKeyList(key, imageUrl);
                        }
                    }
                }

            }

        }
        return pageRepository;
    }
}
