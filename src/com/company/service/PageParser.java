package com.company.service;

import sun.awt.image.ImageWatched;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class PageParser {

    private String internalUrl;

    public PageParser(String internalUrl)
    {
        this.internalUrl = internalUrl;
    }

    public List<String> getAllLinksOnPage(StringBuilder pageContent)
    {
        String link;
        Set<String> uniqueLinks = new LinkedHashSet<>();
        while((link = getLink(pageContent)).length() > 0)
        {
            if (link.startsWith("/")) {
                link = internalUrl + link;
            }
            if (link.startsWith(internalUrl)) {

                uniqueLinks.add(link);
                int linkIndex = pageContent.indexOf(link);
                pageContent.delete(0, linkIndex + link.length());
            }
        }

        return new LinkedList<>(uniqueLinks);
    }

    public static StringBuilder getPageContent(String url)
    {
        StringBuilder sb = new StringBuilder();
        URL startPage = null;
        try {
            startPage = new URL(url);
        } catch (MalformedURLException e) {
            return new StringBuilder();
        }

        try (
                BufferedReader br =
                        new BufferedReader(new InputStreamReader(startPage.openStream()));
        )
        {
            String s;
            while ((s = br.readLine()) != null) {
                sb.append(s+'\n');
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return sb;
    }

    public static String getLink(StringBuilder page)
    {
        String result = "";
        String linkPattern = "<a href=\"";
        int startIndex = page.indexOf(linkPattern);
        if (startIndex > -1)
        {
            int endIndex = page.indexOf("\"", startIndex + linkPattern.length());
            result = page.substring(startIndex + linkPattern.length(), endIndex);
        }
        return result;
    }

    public static String[] getPageTokens(String pageContent)
    {
        String[] tokens = pageContent.split("\\s");
        List<String> result = new LinkedList<>();
        for (String token : tokens)
        {
            if (true) // TODO: filter unncessary tokens
            {
                result.add(token);
            }
        }

        return result;

    }


}
