package com.company.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Index {

    private Map<String, List<String>> index = new HashMap<String, List<String>>();

    public void addToIndex(String keyword, String url)
    {
        if (keyword == null || keyword.length() == 0)
            return;
        List<String> links = index.get(keyword);
        if (links == null)
        {
            links = new LinkedList<String>();
        }
        links.add(url);
        index.put(keyword, links);
    }

    public void addToIndex(String[] keywords, String url){
        for(String key : keywords )
            addToIndex(key, url);
    }

    public Map<String, List<String>> getIndex() {
        return new HashMap<>(index);
    }
}
