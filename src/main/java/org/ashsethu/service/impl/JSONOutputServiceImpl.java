package org.ashsethu.service.impl;


import org.ashsethu.constants.Constants;
import org.ashsethu.service.OutputService;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


@Service
public class JSONOutputServiceImpl implements OutputService {

    @Override
    public void createOutput(ArrayList<String> linkList, ArrayList<String> imageList, String baseDomain) {


        ArrayList<String> internalLinks = new ArrayList<String>();
        ArrayList<String> externalLinks = new ArrayList<String>();
        ArrayList<String> internalImages = new ArrayList<String>();
        ArrayList<String> externalImages = new ArrayList<String>();


        for (String record : linkList) {

            String links[] = record.split(Constants.KEY_SEPARATOR);

            String url = links[links.length - 1];

            if (url.contains(baseDomain)) {

                internalLinks.add(url);

            } else {
                externalLinks.add(url);
            }

        }

        for (String record : imageList) {

            String images[] = record.split(Constants.KEY_SEPARATOR);

            String url = images[images.length - 1];
            System.out.println(url);


            if (url.contains(baseDomain)) {

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
