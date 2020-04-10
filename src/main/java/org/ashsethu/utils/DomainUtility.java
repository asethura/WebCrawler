package org.ashsethu.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DomainUtility {

    //Validate url. If not return null. If valid and not well formed, fix it
    public static String validateAndFormURL(String url, String baseUrl) {

        //check if url starts with http. If so, return url as such
        if (url.startsWith("http")) {
            return url;
        }
        //if not check if starts with /
        if (url.startsWith("/")) {
            return baseUrl + url;
        }
        //if not return null
        return null;
    }

    //check if same domain or not
    public static boolean checkBaseDomain(String url, String baseDomain) {

        if (url.contains(baseDomain)) {
            return true;
        }
        return false;
    }


    public static String extractBaseDomain(String url) {
        //match pattern for base domain and return

        Pattern pattern = Pattern.compile("(http://|https://)?(www\\.)?(.*)");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()){
            return matcher.group(3);
        }
        return null;
    }

    public static String stripQueryString(String url) {
        //match pattern for query Sting and strip

        String baseUrl = url.split("\\?")[0];



        //replace trailing /
        Pattern p = Pattern.compile("/$");
        Matcher m = p.matcher(baseUrl);

        baseUrl = m.replaceAll(""); //remove trailing /

        return baseUrl;
    }

}
