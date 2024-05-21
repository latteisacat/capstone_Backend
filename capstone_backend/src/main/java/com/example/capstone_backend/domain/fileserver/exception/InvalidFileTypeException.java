package com.example.capstone_backend.domain.fileserver.exception;

public class InvalidFileTypeException extends RuntimeException{
    @Override
    public String getMessage() {
        return "지원하지 않는 파일 형식입니다.";
    }
}
