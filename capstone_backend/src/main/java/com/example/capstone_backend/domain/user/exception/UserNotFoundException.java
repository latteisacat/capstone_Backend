package com.example.capstone_backend.domain.user.exception;

public class UserNotFoundException extends RuntimeException{
    @Override
    public String getMessage() {
        return "해당 유저를 찾을 수 없습니다.";
    }
}
