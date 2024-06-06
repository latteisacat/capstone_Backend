package com.example.capstone_backend.domain.user.controller;

import com.example.capstone_backend.common.jwt.CustomUserDetails;
import com.example.capstone_backend.common.util.Tools;
import com.example.capstone_backend.domain.user.ExerciseRepository;
import com.example.capstone_backend.domain.user.UserInfoRepository;
import com.example.capstone_backend.domain.user.dto.request.UserBodySpecEditDTO;
import com.example.capstone_backend.domain.user.dto.request.UserRecordEditDTO;
import com.example.capstone_backend.common.Response;
import com.example.capstone_backend.domain.user.service.UserReadService;
import com.example.capstone_backend.domain.user.service.UserWriteService;
import com.example.capstone_backend.domain.user.util.DummyUserDataCreator;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserReadService userReadService;
    private final UserWriteService userWriteService;

    @PostMapping("/{userId}/modify")
    public ResponseEntity<?> userBodySpecEdit(
            @PathVariable("userId") final Long userId,
            @RequestBody final UserBodySpecEditDTO userBodySpecEditDTO,
            @AuthenticationPrincipal final CustomUserDetails userDetails
    ){
        Tools.invalidUserCheck(userDetails, userId);
        return ResponseEntity.ok(Response.success(userWriteService.userBodySpecEdit(userDetails.getUserInfo(), userBodySpecEditDTO)));
    }

    @PostMapping(value="/{userId}/record", consumes={"multipart/form-data"})
    public ResponseEntity<?> userRecordEdit(
            @PathVariable("userId") final Long userId,
            @RequestPart("exercise") final UserRecordEditDTO userRecordEditDTO,
            @RequestPart(value = "exerciseVideo", required = false) final MultipartFile video,
            @AuthenticationPrincipal final CustomUserDetails userDetails
            ){
        Tools.invalidUserCheck(userDetails, userId);
        return ResponseEntity.ok(Response.success(
                userWriteService.userRecordEdit(userId, userRecordEditDTO, video, userDetails.getUserInfo()
        )));
    }

    @PostMapping(value= "/{userId}/profile", consumes={"multipart/form-data"})
    public ResponseEntity<?> userProfileEdit(
            @PathVariable("userId") final Long userId,
            @RequestPart("profileImage") final MultipartFile profileImage,
            @AuthenticationPrincipal final CustomUserDetails userDetails
    ){
        Tools.invalidUserCheck(userDetails, userId);
        return ResponseEntity.ok(Response.success(
                userWriteService.userProfileEdit(userId, profileImage, userDetails.getUserInfo()
                )));
    }

    @GetMapping(value= "/{userId}/profile")
    public ResponseEntity<?> userProfile(
            @PathVariable("userId") final Long userId
    ){
        return ResponseEntity.ok(userReadService.getUserProfileRequest(userId));
    }


    // TODO: 나중에 삭제하기
//    @PostMapping("/dummy")
//    public ResponseEntity<?> dummy(){
//        DummyUserDataCreator dummy = new DummyUserDataCreator();
//        dummy.createDummy(userInfoRepository, exerciseRepository);
//        return ResponseEntity.ok(Response.success("dummy"));
//    }

}
