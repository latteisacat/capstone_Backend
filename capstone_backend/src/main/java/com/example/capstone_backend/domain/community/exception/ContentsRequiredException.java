package com.example.capstone_backend.domain.community.exception;

public class ContentsRequiredException extends RuntimeException{
    @Override
    public String getMessage() {
        return "이미지나 비디오 중 하나는 필수입니다.";
    }
}
