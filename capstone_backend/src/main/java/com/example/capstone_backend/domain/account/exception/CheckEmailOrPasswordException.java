package com.example.capstone_backend.domain.account.exception;

public class CheckEmailOrPasswordException extends RuntimeException{
    @Override
    public String getMessage() {
        return "이메일 또는 비밀번호를 확인해주세요.";
    }
}
