package com.example.capstone_backend.domain.account.exception;

public class AlreadyExistEmailException extends RuntimeException{
    @Override
    public String getMessage() {
        return "이미 존재하는 이메일입니다.";
    }
}
