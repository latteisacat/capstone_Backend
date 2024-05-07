package com.example.capstone_backend.domain.user.controller;


import com.example.capstone_backend.domain.user.dto.request.UserBodySpecEditDTO;
import com.example.capstone_backend.domain.user.dto.request.UserRecordEditDTO;
import com.example.capstone_backend.domain.user.dto.response.UserBodySpecEditResponseDTO;
import com.example.capstone_backend.domain.user.dto.response.UserInfoResponseDTO;
import com.example.capstone_backend.domain.user.dto.response.UserProfileEditResponseDTO;
import com.example.capstone_backend.domain.user.dto.response.UserRecordEditResponseDTO;
import com.example.capstone_backend.common.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    @PostMapping("/{userId}/modify")
    public ResponseEntity<?> userBodySpecEdit(
            @PathVariable("userId") final Integer userId,
            @RequestBody final UserBodySpecEditDTO userBodySpecEditDTO
    ){
        return ResponseEntity.ok(Response.success(dummyBodySpecEditResponse()));
    }

    @PostMapping(value="/{userId}/record", consumes={"multipart/form-data"})
    public ResponseEntity<?> userRecordEdit(
            @PathVariable("userId") final Integer userId,
            @RequestPart("exercise") final UserRecordEditDTO userRecordEditDTO,
            @RequestPart(value="exerciseVideo", required = true) final MultipartFile video
    ){
        return ResponseEntity.ok(Response.success(dummyRecordEditResponseDTO()));
    }

    @PostMapping(value= "/{userId}/profile", consumes={"multipart/form-data"})
    public ResponseEntity<?> userProfileEdit(
            @PathVariable("userId") final Integer userId,
            @RequestPart("profileImage") final MultipartFile profileImage
    ){
        return ResponseEntity.ok(Response.success(dummyUserProfileEditResponseDTO()));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> userInfo(@PathVariable("userId") final Integer userId){
        return ResponseEntity.ok(Response.success(dummyUserInfoResponseDTO()));
    }

    private static UserBodySpecEditResponseDTO dummyBodySpecEditResponse() {
        UserBodySpecEditResponseDTO userBodySpecEditResponseDTO = UserBodySpecEditResponseDTO
                .builder()
                .userId(1L)
                .bodyFat(9.8)
                .weight(70.0)
                .height(180.0)
                .BMI(21.60)
                .percentageFat(13.0)
                .muscleMass(35.0)
                .build();
        return userBodySpecEditResponseDTO;
    }



    private static UserRecordEditResponseDTO dummyRecordEditResponseDTO(){
        List<UserRecordEditResponseDTO.UserRecord> userRecords = new ArrayList<>();
        userRecords.add(UserRecordEditResponseDTO.UserRecord.builder()
                        .sportName("데드리프트")
                        .record("160kg")
                        .percentage("30%")
                        .contents("/video/asf.mp4")
                .build());
        userRecords.add(UserRecordEditResponseDTO.UserRecord.builder()
                .sportName("스쿼트")
                .record("130kg")
                .percentage("30%")
                .contents("/video/asdf.mp4")
                .build());
        userRecords.add(UserRecordEditResponseDTO.UserRecord.builder()
                .sportName("벤치프레스")
                .record("100kg")
                .percentage("30%")
                .contents("/video/asfd.mp4")
                .build());
        UserRecordEditResponseDTO userRecordEditResponseDTO = UserRecordEditResponseDTO
                .builder()
                .userId(1L)
                .userRecords(userRecords)
                .build();
        return userRecordEditResponseDTO;
    }



    private static UserProfileEditResponseDTO dummyUserProfileEditResponseDTO(){
        UserProfileEditResponseDTO userProfileEditResponseDTO = UserProfileEditResponseDTO
                .builder()
                .userId(1L)
                .userName("황지원")
                .userProfile("/image/asdf.jpg")
                .build();
        return userProfileEditResponseDTO;
    }

    private static UserInfoResponseDTO dummyUserInfoResponseDTO(){
        List<UserInfoResponseDTO.UserRecord> userRecords = new ArrayList<>();
        userRecords.add(UserInfoResponseDTO.UserRecord.builder()
                .sportName("데드리프트")
                .record("160kg")
                .percentage("30%")
                .contents("/video/asf.mp4")
                .build());
        userRecords.add(UserInfoResponseDTO.UserRecord.builder()
                .sportName("스쿼트")
                .record("130kg")
                .percentage("30%")
                .contents("/video/asdf.mp4")
                .build());
        userRecords.add(UserInfoResponseDTO.UserRecord.builder()
                .sportName("벤치프레스")
                .record("100kg")
                .percentage("30%")
                .contents("/video/asfd.mp4")
                .build());
        return UserInfoResponseDTO.builder()
                .userId(1l)
                .name("황지원")
                .profile("/image/asdf.jpg")
                .height(180.0)
                .weight(70.0)
                .muscleMass(35.0)
                .bodyFat(9.8)
                .BMI(21.60)
                .percentageFat(13.0)
                .userPercentage("30%")
                .userRecords(userRecords)
                .build();
    }

}
