package com.company;

import com.company.db.IndexDAO;
import com.company.service.Index;
import com.company.service.PageParser;
import java.util.*;

public class Main {

    public static String internalUrl = "https://udacity.github.io";
    public static String startPage = "/cs101x/";

    public static void main(String[] args) {

        String startUrl = internalUrl + startPage;
        PageParser parser = new PageParser(internalUrl);
        Index idx = new Index();

        LinkedList<String> pagesToVisit = new LinkedList<>();
        List<String> visitedLinks = new LinkedList<>();
        pagesToVisit.add(startUrl);

        while (pagesToVisit.size() > 0)
        {
            String nextUrl = pagesToVisit.removeFirst();
            visitedLinks.add(nextUrl);

            StringBuilder pageContent = PageParser.getPageContent(nextUrl);
            String[] words = PageParser.getPageTokens(pageContent.toString());
            idx.addToIndex(words, nextUrl);

            List<String> newLinks = parser.getAllLinksOnPage(pageContent);
            for (String l : newLinks)
            {
                if (!visitedLinks.contains(l))
                {
                    pagesToVisit.addLast(l);
                }
            }
        }

        IndexDAO.persistIndex(idx.getIndex());

    }

}
