package org.ashsethu.model;

import java.util.ArrayList;

public class ExternalImages {

    public ArrayList<URL> getUrls() {
        return urls;
    }

    public void setUrls(ArrayList<URL> urls) {
        this.urls = urls;
    }

    private ArrayList<URL> urls = new ArrayList<URL>();

    public void addUrl(URL url){
        urls.add(url);
    }
}
