package com.imageloader.service;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ImageLoader {
    private static class ImageLoaderHolder {
        private static final ImageLoader instance = new ImageLoader();
    }

    private ImageLoader() {}

    public static ImageLoader getInstance() {
        return ImageLoaderHolder.instance;
    }

    public void downloadImages(String directory, List<URL> imgUrls)
            throws FileNotFoundException, IOException, InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<Future<Void>> futures = new ArrayList<>();
        for (URL imgUrl : imgUrls) {
            Future<Void> future = executorService.submit(prepareFileAndDownloadImage(directory, imgUrl));
            futures.add(future);
        }

        for (Future<Void> future : futures) {
            future.get();
        }

        executorService.shutdown();
    }

    private Callable<Void> prepareFileAndDownloadImage(String directory, URL imgUrl)
            throws FileNotFoundException, IOException {
        return () -> {
            String[] urlParts = imgUrl.toString().split("/");
            StringBuilder destinationFilePath = new StringBuilder(directory);
            destinationFilePath.append("/").append(urlParts[urlParts.length - 1]);
            File destinationFile = new File(destinationFilePath.toString());
            if (!(destinationFile.exists() && destinationFile.canWrite())) {
                if (!destinationFile.createNewFile()) {
                    throw new FileNotFoundException("Internal error. Can't download image: " +
                            imgUrl + " . File: " + destinationFile);
                }
            }
            downloadImage(imgUrl, destinationFile);
            return null;
        };
    }

    private synchronized void downloadImage(URL imgUrl, File destinationFile)
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
