package com.example.capstone_backend.domain.account.exception;

public class UserPermissionDeniedException extends RuntimeException{
    @Override
    public String getMessage() {
        return "해당 유저의 정보를 수정할 권한이 없습니다.";
    }
}
