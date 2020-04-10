package org.ashsethu.service;

import org.ashsethu.repository.PageRepository;
import org.json.simple.JSONObject;

import java.io.IOException;

public interface CrawlerService {
    public PageRepository crawlTheWeb(String startingUrl, String baseDomaain) throws IOException;
}
