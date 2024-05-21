package com.example.capstone_backend.domain.fileserver.exception;

public class InvalidVideoTypeException extends RuntimeException{
    @Override
    public String getMessage() {
        return "비디오 파일의 형식이 잘못되었습니다.";
    }
}
