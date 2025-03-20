package com.imageloader.exception;

public class DirectoryAccessException extends RuntimeException {
    private static final String defaultMessage = "Cannot access directory.";

    public DirectoryAccessException() {
      super(defaultMessage);
    }

    public DirectoryAccessException(String message) {
      super(message);
    }
}
