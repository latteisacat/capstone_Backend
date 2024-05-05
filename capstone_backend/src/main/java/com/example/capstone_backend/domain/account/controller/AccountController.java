package com.example.capstone_backend.domain.account.controller;

import com.example.capstone_backend.domain.account.dto.request.JoinRequestDTO;
import com.example.capstone_backend.domain.account.dto.request.LoginRequestDTO;
import com.example.capstone_backend.common.Response;
import com.example.capstone_backend.domain.account.dto.response.LoginResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("")
public class AccountController {
    //login api
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        LoginResponseDTO loginResponseDTO = LoginResponseDTO.builder()
                .userId(1L)
                .build();
        return ResponseEntity.ok(Response.success(loginResponseDTO));
    }

    //join api
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody JoinRequestDTO joinRequest) {
        return ResponseEntity.ok(Response.success(null));
    }
}
