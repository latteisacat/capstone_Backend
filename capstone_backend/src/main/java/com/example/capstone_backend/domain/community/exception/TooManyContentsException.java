package com.example.capstone_backend.domain.community.exception;

public class TooManyContentsException extends RuntimeException{
    @Override
    public String getMessage() {
        return "이미지나 동영상중 하나만 업로드 가능합니다.";
    }
}
