package com.example.capstone_backend.domain.fileserver;

public class S3Util {
    public static String getFileNameFromUrl(final String url) {
        final String[] splitUrl = url.split("/");
        return splitUrl[splitUrl.length - 1];
    }
}