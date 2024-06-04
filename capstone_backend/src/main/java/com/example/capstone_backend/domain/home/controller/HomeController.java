package com.example.capstone_backend.domain.home.controller;


import com.example.capstone_backend.common.jwt.CustomUserDetails;
import com.example.capstone_backend.common.util.Tools;
import com.example.capstone_backend.domain.home.service.HomeReadService;
import com.example.capstone_backend.domain.home.service.HomeWriteService;
import com.example.capstone_backend.domain.user.dto.request.UserCompetitorAddDTO;
import com.example.capstone_backend.common.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/home")
public class HomeController {

    final private HomeReadService homeReadService;
    final private HomeWriteService homeWriteService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> userHome(
            @PathVariable("userId") final Long userId
    ){
        return ResponseEntity.ok(Response.success(homeReadService.getUserHome(userId)));
    }

    @PostMapping("/{userId}/addCompetitor")
    public ResponseEntity<?> addCompetitor(
            @PathVariable("userId") final UserCompetitorAddDTO userCompetitorAddDTO,
            @AuthenticationPrincipal final CustomUserDetails userDetails
    ){
        Tools.invalidUserCheck(userDetails, userCompetitorAddDTO.userId());
        homeWriteService.addCompetitor(userCompetitorAddDTO, userDetails.getUserInfo());
        return ResponseEntity.ok(Response.success(null));
    }
}
