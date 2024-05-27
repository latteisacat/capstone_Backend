package com.example.capstone_backend.domain.fileserver.exception;

public class AmazonS3SaveError extends RuntimeException{
    String message;
    public AmazonS3SaveError(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "Amazon S3 save error: " + message;
    }
}
