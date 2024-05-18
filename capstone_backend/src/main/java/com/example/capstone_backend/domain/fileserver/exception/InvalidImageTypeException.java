package com.example.capstone_backend.domain.fileserver.exception;

public class InvalidImageTypeException extends RuntimeException{
    @Override
    public String getMessage() {
        return "이미지 파일의 형식이 잘못되었습니다.";
    }
}
