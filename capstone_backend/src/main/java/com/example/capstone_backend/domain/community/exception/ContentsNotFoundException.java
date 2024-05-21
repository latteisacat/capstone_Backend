package com.example.capstone_backend.domain.community.exception;

public class ContentsNotFoundException extends RuntimeException{
    @Override
    public String getMessage() {
        return "게시물을 찾을 수 없습니다.";
    }
}
