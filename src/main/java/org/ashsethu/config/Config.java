package org.ashsethu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Value("${crawler.thread.count}")
    private int maxThread;

    @Value("${crawler.depth.max}")
    private int maxDepth;

    @Value("${crawler.page.max}")
    private int maxPages;

    @Value("${crawler.external}")
    private boolean allowExternal;

    @Value("${crawler.starting.url}")
    private String startingUrl;

    public int getMaxThread() {
        return maxThread;
    }

    public void setMaxThread(int maxThread) {
        this.maxThread = maxThread;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public int getMaxPages() {
        return maxPages;
    }

    public void setMaxPages(int maxPages) {
        this.maxPages = maxPages;
    }

    public boolean isAllowExternal() {
        return allowExternal;
    }

    public void setAllowExternal(boolean allowExternal) {
        this.allowExternal = allowExternal;
    }

    public String getStartingUrl() {
        return startingUrl;
    }

    public void setStartingUrl(String startingUrl) {
        this.startingUrl = startingUrl;
    }



}
