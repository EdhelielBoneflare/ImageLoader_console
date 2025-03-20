package com.imageloader.service;

import java.io.*;
import java.net.URL;
import java.util.List;

public class ImageLoader {
    private static ImageLoader instance = null;

    private ImageLoader() {}

    public static ImageLoader getInstance() {
        if (instance == null) {
            instance = new ImageLoader();
        }
        return instance;
    }

    public void downloadImages(String directory, List<URL> imgUrls)
            throws FileNotFoundException, IOException {
        for (URL imgUrl : imgUrls) {
            String[] urlParts = imgUrl.toString().split("/");
            StringBuilder destinationFilePath = new StringBuilder(directory);
            destinationFilePath.append("/").append(urlParts[urlParts.length - 1]);
            File destinationFile = new File(destinationFilePath.toString());
            if (!(destinationFile.exists() && destinationFile.canWrite())) {
                throw new FileNotFoundException("Internal error. Can't download image: " +
                        imgUrl + " . File: " + destinationFile);
            }
            downloadImage(imgUrl, destinationFile);
        }

    }

    private void downloadImage(URL imgUrl, File destinationFile)
            throws IOException {
        try (InputStream in = imgUrl.openStream();
             OutputStream out = new FileOutputStream(destinationFile)) {
            byte[] buffer = new byte[2048];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }
}
