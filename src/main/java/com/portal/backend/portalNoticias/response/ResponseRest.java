package com.portal.backend.portalNoticias.response;

import java.util.ArrayList;
import java.util.HashMap;

public class ResponseRest {

    private ArrayList<HashMap<String, String>> metadata = new ArrayList<>();

    public ArrayList<HashMap<String, String>> getMetadata() {
        return metadata;
    }

    public void setMetadata(String type, String code, String date){
        HashMap<String, String> metadata = new HashMap<>();
        metadata.put("type", type);
        metadata.put("code", code);
        metadata.put("date", date);
        this.metadata.add(metadata);
    }
}
