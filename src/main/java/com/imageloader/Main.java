package com.imageloader;

import com.imageloader.exception.InternalErrorHandler;
import com.imageloader.service.HTMLParser;
import com.imageloader.exception.DirectoryAccessException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        HTMLParser htmlParser = HTMLParser.getInstance();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Provide URL for image download: ");
        String url = null;
        while (url == null) {
            url = scanner.nextLine().trim();
            if (url.isEmpty()) {
                System.out.println("URL cannot be empty. Please enter a valid URL.");
                url = null;
            }
        }

        System.out.print("Enter directory path to save images: ");
        String directoryPath = null;
        while (directoryPath == null) {
            directoryPath = scanner.nextLine().trim();
            if (directoryPath.isEmpty()) {
                System.out.println("Directory path cannot be empty. Please enter a valid path.");
                directoryPath = null;
            }
        }

        try {
            htmlParser.getPageImages(url, directoryPath);
        } catch (MalformedURLException | DirectoryAccessException e) {
            System.out.println("Error:" + e.getMessage());
        } catch (IOException e) {
            InternalErrorHandler.handleInternalException(e);
        }

        scanner.close();
    }

}