package com.example.capstone_backend.domain.home.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/home")
public class HomeController {

    @GetMapping("/{userId}")
    public ResponseEntity<?> userHome(@PathVariable("userId") final Integer userId){
        return ResponseEntity.ok(userId);
    }

}
