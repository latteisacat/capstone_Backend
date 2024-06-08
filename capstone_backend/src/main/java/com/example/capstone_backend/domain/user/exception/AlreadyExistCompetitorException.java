package com.example.capstone_backend.domain.user.exception;

public class AlreadyExistCompetitorException extends RuntimeException{
    @Override
    public String getMessage() {
        return "이미 경쟁자로 등록되어 있습니다.";
    }
}
