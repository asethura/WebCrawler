package org.ashsethu.utils;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class HtmlParser {

    protected static final Logger logger = LoggerFactory.getLogger(HtmlParser.class);

    public static Elements getLinks(String URL) throws IOException {

        //1. Fetch the HTML code
        try{
            Document document = Jsoup.connect(URL).get();
            //2. Parse the HTML to extract links to other URLs
            Elements linksOnPage = document.select("a[href]");

            for (Element page: linksOnPage){
                logger.debug("Link " + page.attr("href"));
            }

            return linksOnPage;
        }catch(HttpStatusException he){
            logger.warn(he.toString());
            logger.warn("Page not reachable " + URL);
        }
        return null;
    }

    public static Elements getImages(String URL) throws IOException {

        //1. Fetch the image
        try{
            Document document = Jsoup.connect(URL).get();
            //2. Parse the HTML to extract links to other URLs
            Elements imagesOnPage = document.select("img");

            for (Element page: imagesOnPage){
                logger.debug("Image " + page.attr("src"));
            }

            return imagesOnPage;
        }catch(HttpStatusException he){
            logger.debug(he.toString());
            logger.debug("Page not reachable " + URL);
        }
        return null;
    }
}
