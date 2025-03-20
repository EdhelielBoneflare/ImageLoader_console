package com.imageloader.utils;

import com.imageloader.exception.DirectoryAccessException;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class Validator {

    public static void validateUrl(String urlString) throws MalformedURLException {
        new URL(urlString);
    }

    public static void validateDirectory(String path) throws DirectoryAccessException {
        boolean result;

        File directory = new File(path);
        if (!directory.exists()) {
            result = directory.mkdirs();
        } else {
            result = directory.isDirectory() && directory.canWrite();
        }

        if (!result) {
            throw new DirectoryAccessException("Cannot access directory: " + path);
        }
    }
}
