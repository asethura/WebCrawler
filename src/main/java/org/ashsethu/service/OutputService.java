package org.ashsethu.service;



import org.ashsethu.model.Response;

import java.util.ArrayList;

public interface OutputService {

    public Response createOutput(ArrayList<String> linkList, ArrayList<String> imageList, String baseDomain);
}
