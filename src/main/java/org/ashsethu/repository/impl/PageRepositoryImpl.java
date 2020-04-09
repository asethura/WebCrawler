package org.ashsethu.repository.impl;

import org.ashsethu.constants.Constants;

import org.ashsethu.repository.PageRepository;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class PageRepositoryImpl implements PageRepository {

    private static ArrayList<String> pageKeyList = new ArrayList<String>();

    private static ArrayList<String> imageKeyList = new ArrayList<String>();

    private static ArrayList<String> toBeCrawledList = new ArrayList<String>();


    public synchronized void addToPageKeyList(String key, String url) {

        String newKey = key + Constants.KEY_SEPARATOR + url;
        pageKeyList.add(newKey);
    }

    public synchronized void addToImageKeyList(String key, String url) {

        String newKey = key + Constants.KEY_SEPARATOR + url;
        imageKeyList.add(newKey);
    }

    public synchronized void addToBeCrawledList(String key, String url) {

        String newKey = key + Constants.KEY_SEPARATOR + url;
        toBeCrawledList.add(newKey);

    }

    public synchronized int getPageListCount() {
        return pageKeyList.size();
    }

    public synchronized int getCrawlListCount() {
        return toBeCrawledList.size();
    }


    public synchronized String popFromCrawList() {
        String topItem = toBeCrawledList.get(0);
        toBeCrawledList.remove(topItem);
        return topItem;
    }

    public void initiateCrawlList(String startingURL) {
        toBeCrawledList.add(startingURL);
    }

    @Override
    public ArrayList<String> getAllPages() {
        return pageKeyList;
    }

    @Override
    public ArrayList<String> getAllImages() {
        return imageKeyList;
    }

    public void print(){
        System.out.println(pageKeyList.toString());
        System.out.println(imageKeyList.toString());
    }
}
