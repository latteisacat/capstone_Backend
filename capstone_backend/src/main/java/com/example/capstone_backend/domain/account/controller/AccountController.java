package com.example.capstone_backend.domain.account.controller;

import com.example.capstone_backend.domain.account.dto.request.JoinRequestDTO;
import com.example.capstone_backend.domain.account.dto.request.LoginRequestDTO;
import com.example.capstone_backend.common.Response;
import com.example.capstone_backend.domain.account.dto.response.LoginResponseDTO;
import com.example.capstone_backend.domain.account.exception.AlreadyExistEmailException;
import com.example.capstone_backend.domain.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("")
public class AccountController {
    private final AccountService accountService;

    //login api
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
//        // accountService.login(loginRequest);
//        return ResponseEntity.ok(Response.success(
//                LoginResponseDTO.builder()
//                .userId(1L)
//                .build()
//        ));
//    }

    //join api
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody JoinRequestDTO joinRequest) {
        if(accountService.checkEmail(joinRequest.email())) {
            accountService.join(joinRequest);
            return ResponseEntity.ok(Response.success("가입 성공"));
        }
        else {
            throw new AlreadyExistEmailException();
        }
    }
}
