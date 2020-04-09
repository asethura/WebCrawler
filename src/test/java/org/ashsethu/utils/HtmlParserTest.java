package org.ashsethu.utils;

import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;


public class HtmlParserTest {

    @Test
    public void noLinks_Test() throws IOException {
        HtmlParser htmlParser = new HtmlParser();
        //Testing a site with no links
        Elements pageLinks = htmlParser.getLinks("http://www.brainjar.com/java/host/test.html");
        assert(pageLinks.isEmpty());
    }

    @Test
    public void MultiLink_Test() throws IOException {
        HtmlParser htmlParser = new HtmlParser();
        Elements pageLinks = htmlParser.getLinks("https://asethura.wixsite.com/techfortech");
        assert(pageLinks.size()==14);
    }
}