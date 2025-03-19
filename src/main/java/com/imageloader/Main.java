package com.imageloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Provide URL for image download: ");
        String urlInput = scanner.nextLine();
        try {
            URL url = new URL(urlInput);
        } catch (MalformedURLException e) {
            System.out.println("Wrong format of the URL. Please provide valid URL.");
        }

    }


}