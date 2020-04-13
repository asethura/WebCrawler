package org.ashsethu.utils;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.apache.commons.lang3.StringEscapeUtils;
import org.ashsethu.service.impl.CrawlerServiceImpl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HtmlParserTest {

    protected static final Logger logger = LoggerFactory.getLogger(CrawlerServiceImpl.class);

    @Test
    public void noLinks_Test() throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8001), 0);
        server.createContext("/test", new MyHttpHandler());
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        server.setExecutor(threadPoolExecutor);
        server.start();

        logger.info(" Server started on port 8001");

        HtmlParser htmlParser = new HtmlParser();
        //Testing a site with no links
        Elements pageLinks = htmlParser.getLinks("http://localhost:8001/test");

        assert (pageLinks.isEmpty());
        server.stop(0);
    }

    @Test
    public void MultiLink_Test() throws IOException, InterruptedException {

        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8001), 0);
        server.createContext("/test", new MyHttpHandlerWthTwoLinks());
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        server.setExecutor(threadPoolExecutor);
        server.start();

        logger.info(" Server started on port 8001");


        HtmlParser htmlParser = new HtmlParser();
        //Elements pageLinks = htmlParser.getLinks("https://asethura.wixsite.com/techfortech");
        Elements pageLinks = htmlParser.getLinks("http://localhost:8001/test");

        logger.info(" pageLinks.size() " + pageLinks.size());
        assert (pageLinks.size() == 2);
        server.stop(0);
    }

    private Elements createElementList() {
        Element el1 = new Element("a").attr("href", "http://linkedin.com");
        Element el2 = new Element("a").attr("href", "http://twitter.com");
        Element el3 = new Element("a").attr("href", "http://google.com");
        Elements els = new Elements();
        els.add(el1);
        els.add(el2);
        els.add(el3);
        return els;
    }

    private class MyHttpHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String requestParamValue = null;

            if ("CONNECT".equals(httpExchange.getRequestMethod())) {

                requestParamValue = handleGetRequest(httpExchange);

            }
            handleResponse(httpExchange, requestParamValue);
        }

        private String handleGetRequest(HttpExchange httpExchange) {
            return httpExchange.
                    getRequestURI()
                    .toString()
                    .split("\\?")[1]
                    .split("=")[1];
        }

        private void handleResponse(HttpExchange httpExchange, String requestParamValue) throws IOException {
            OutputStream outputStream = httpExchange.getResponseBody();
            StringBuilder htmlBuilder = new StringBuilder();
            htmlBuilder.append("<html>").
                    append("<body>").
                    append("<h1>").
                    append("Hello ")
                    .append(requestParamValue)
                    .append("</h1>")
                    .append("</body>")
                    .append("</html>");
            String htmlResponse = StringEscapeUtils.escapeHtml4(htmlBuilder.toString());
            httpExchange.sendResponseHeaders(200, htmlResponse.length());
            outputStream.write(htmlResponse.getBytes());
            outputStream.flush();
            outputStream.close();
        }
    }

    private class MyHttpHandlerWthTwoLinks implements HttpHandler {

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String requestParamValue = null;

            if ("CONNECT".equals(httpExchange.getRequestMethod())) {

                requestParamValue = handleGetRequest(httpExchange);

            }
            handleResponse(httpExchange, requestParamValue);
        }

        private String handleGetRequest(HttpExchange httpExchange) {
            return httpExchange.
                    getRequestURI()
                    .toString()
                    .split("\\?")[1]
                    .split("=")[1];
        }

        private void handleResponse(HttpExchange httpExchange, String requestParamValue) throws IOException {
            OutputStream outputStream = httpExchange.getResponseBody();
            StringBuilder htmlBuilder = new StringBuilder();
            htmlBuilder.append("<html>").
                    append("<body>").
                    append("<h1>").
                    append("Hello ")
                    .append(requestParamValue)
                    .append("</h1>")
                    .append("<a href=\"#\">").
                    append("Level 1")
                    .append("</a>")
                    .append("<a href=\"#\">").
                    append("Level 2")
                    .append("</a>")
                    .append("</body>")
                    .append("</html>");
            //String htmlResponse = StringEscapeUtils.escapeHtml4(htmlBuilder.toString());
            String htmlResponse = htmlBuilder.toString();
            logger.info(htmlResponse);
            httpExchange.sendResponseHeaders(200, htmlResponse.length());
            outputStream.write(htmlResponse.getBytes());
            outputStream.flush();
            outputStream.close();
        }
    }
}


