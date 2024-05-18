package com.example.capstone_backend.domain.account.exception;

public class ForbiddenException extends RuntimeException{
    @Override
    public String getMessage() {
        return "권한이 없습니다.";
    }
}
