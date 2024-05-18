package com.example.capstone_backend.domain.fileserver.exception;

public class NotFoundVideoException extends RuntimeException{
    @Override
    public String getMessage() {
        return "비디오 파일을 찾을 수 없습니다.";
    }
}
