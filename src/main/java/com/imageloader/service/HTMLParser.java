package com.imageloader.service;

import com.imageloader.exception.DirectoryAccessException;
import com.imageloader.utils.Validator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class HTMLParser {
    private static HTMLParser instance = null;
    private static ImageLoader imageLoader;

    private HTMLParser() {
        imageLoader = ImageLoader.getInstance();
    }

    public static HTMLParser getInstance() {
        if (instance == null) {
            instance = new HTMLParser();
        }
        return instance;
    }

    public void getPageImages(String url, String directoryPath)
            throws MalformedURLException, DirectoryAccessException, IOException,
            InterruptedException, ExecutionException {
        Validator.validateUrl(url);
        Validator.validateDirectory(directoryPath);


        Document webPage = fetchHTML(url);
        List<URL> imgURLS = extractImgURLS(webPage, url);
        imageLoader.downloadImages(directoryPath, imgURLS);
    }

    private Document fetchHTML(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

    private List<URL> extractImgURLS(Document webPage, String url) throws MalformedURLException {
        List<URL> imgUrls = new ArrayList<>();

        Elements imgElements = webPage.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
        for (Element element: imgElements) {
            String imgSrc = element.attr("src");
            if (!imgSrc.startsWith("http")) {
                imgSrc = url + imgSrc;
            }
            Validator.validateUrl(imgSrc);
            imgUrls.add(new URL(imgSrc));
        }
        return imgUrls;
    }


}
