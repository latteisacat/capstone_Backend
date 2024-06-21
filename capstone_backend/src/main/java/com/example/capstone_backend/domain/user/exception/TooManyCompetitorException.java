package com.example.capstone_backend.domain.user.exception;

public class TooManyCompetitorException extends RuntimeException{
    @Override
    public String getMessage() {
        return "경쟁자는 최대 3명까지 등록 가능합니다.";
    }
}
