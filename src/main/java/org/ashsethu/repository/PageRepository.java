package org.ashsethu.repository;

import org.jsoup.nodes.Element;

import java.util.ArrayList;

public interface PageRepository {

    public  void  addToPageKeyList(String key, String url);

    public  void  addToImageKeyList(String key, String url);

    public  void  addToBeCrawledList(String key, String url);

    public  int  getPageListCount();

    public  int  getCrawlListCount();

    public  String  popFromCrawList();

    public  void initiateCrawlList(String startingURL);

    public ArrayList<String> getAllPages();

    public ArrayList<String> getAllCrawlPages();

    public ArrayList<String> getAllImages();
}
