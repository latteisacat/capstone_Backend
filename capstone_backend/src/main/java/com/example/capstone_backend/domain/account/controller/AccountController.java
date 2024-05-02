package com.example.capstone_backend.domain.account.controller;

import com.example.capstone_backend.domain.account.dto.request.JoinRequestDTO;
import com.example.capstone_backend.domain.account.dto.request.LoginRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


public class AccountController {
    //login api
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        return ResponseEntity.ok(null);
    }

    //join api
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody JoinRequestDTO joinRequest) {
        return ResponseEntity.ok(null);
    }
}
