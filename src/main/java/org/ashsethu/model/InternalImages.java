package org.ashsethu.model;

import java.util.ArrayList;

public class InternalImages {
    private ArrayList<URL> urls = new ArrayList<URL>();

    public ArrayList<URL> getUrls() {
        return urls;
    }

    public void setUrls(ArrayList<URL> urls) {
        this.urls = urls;
    }

    public void addUrl(URL url){
        urls.add(url);
    }
}
