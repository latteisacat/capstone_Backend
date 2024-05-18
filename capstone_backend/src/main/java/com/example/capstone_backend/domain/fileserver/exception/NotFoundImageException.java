package com.example.capstone_backend.domain.fileserver.exception;

public class NotFoundImageException extends RuntimeException{
    @Override
    public String getMessage() {
        return "이미지 파일을 찾을 수 없습니다.";
    }
}
