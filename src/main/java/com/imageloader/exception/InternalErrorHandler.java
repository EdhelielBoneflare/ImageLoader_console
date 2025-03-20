package com.imageloader.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class InternalErrorHandler {
    private static final Logger logger = LoggerFactory.getLogger(InternalErrorHandler.class);

    public static String generateUniqueId() {
        return UUID.randomUUID().toString();
    }

    public static void handleInternalException(Exception e) {
        String id = generateUniqueId();
        logger.error("ID: " + id + ". " + e.getMessage());
        System.out.println("Internal error. Please, address support.\nID: " + id);
    }
}
