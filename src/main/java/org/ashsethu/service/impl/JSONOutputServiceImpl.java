package org.ashsethu.service.impl;

import org.ashsethu.config.SessionConfigFactory;
import org.ashsethu.constants.Constants;
import org.ashsethu.service.OutputService;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


@Service
public class JSONOutputServiceImpl implements OutputService {

    @Override
    public void createOutput(ArrayList<String> linkList, ArrayList<String> imageList) {

        /*
        JSONObject externalLink = new JSONObject();
        JSONArray externalLinks = new JSONArray();
        JSONObject internalLink = new JSONObject();
        JSONArray internalLinks = new JSONArray();
        JSONObject externalImage = new JSONObject();
        JSONArray externalImages = new JSONArray();
        JSONObject internalImage = new JSONObject();
        JSONArray internalImages = new JSONArray();
        JSONObject finalLinksJSON = new JSONObject();
        JSONObject finalImagesJSON = new JSONObject();
        JSONObject finalJSON = new JSONObject();


        for (String record : linkList) {

            String links[] = record.split(Constants.KEY_SEPARATOR);

            String url = links[links.length - 1];
            System.out.println(url);
            //url = url.replace("\\", "");

            if (url.contains(ConfigFactory.getConfig().getBaseDomain())) {

                internalLink.put("url", url);
                internalLinks.add(internalLink);
            } else {
                externalLink.put("url", url);
                externalLinks.add(externalLink);
            }

        }

        for (String record : imageList) {

            String images[] = record.split(Constants.KEY_SEPARATOR);

            String url = images[images.length - 1];
            System.out.println(url);


            if (url.contains(ConfigFactory.getConfig().getBaseDomain())) {

                internalImage.put("url", url);
                internalImages.add(internalImage);
            } else {

                externalImage.put("url", url);
                externalImages.add(externalImage);
            }

        }


        finalLinksJSON.put("external", externalLinks);
        finalLinksJSON.put("internal", internalLinks);

        System.out.println(externalImages.toJSONString().replace("\\", ""));
        finalImagesJSON.put("external", externalImages);
        finalImagesJSON.put("internal", internalImages);

        finalJSON.put("links", finalLinksJSON);
        finalJSON.put("images", finalImagesJSON);

        try (FileWriter file = new FileWriter("crawlMap.json")) {

            file.write(finalJSON.toJSONString().replace("\\", ""));
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();

        }
       */

        ArrayList<String> internalLinks = new ArrayList<String>();
        ArrayList<String> externalLinks = new ArrayList<String>();
        ArrayList<String> internalImages = new ArrayList<String>();
        ArrayList<String> externalImages = new ArrayList<String>();


        for (String record : linkList) {

            String links[] = record.split(Constants.KEY_SEPARATOR);

            String url = links[links.length - 1];

            if (url.contains(SessionConfigFactory.getSessionConfig().getBaseDomain())) {

                internalLinks.add(url);

            } else {
                externalLinks.add(url);
            }

        }

        for (String record : imageList) {

            String images[] = record.split(Constants.KEY_SEPARATOR);

            String url = images[images.length - 1];
            System.out.println(url);


            if (url.contains(SessionConfigFactory.getSessionConfig().getBaseDomain())) {

                internalImages.add(url);

            } else {
                externalImages.add(url);
            }

        }


        try (FileWriter file = new FileWriter("crawlMap.dat")) {
            file.write("Internal Links:\n");


            for (String record : internalLinks) {
                file.write(record + "\n");
            }

            file.write("\n");
            file.write("External Links:\n");
            for (String record : externalLinks) {
                file.write(record + "\n");
            }

            file.write("\n");
            file.write("Internal Images:\n");
            for (String record : internalImages) {
                file.write(record + "\n");
            }

            file.write("\n");
            file.write("External Images:\n");
            for (String record : externalImages) {
                file.write(record + "\n");
            }
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }


}
