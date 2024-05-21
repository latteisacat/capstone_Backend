package com.example.capstone_backend.domain.user.service;

import com.example.capstone_backend.domain.user.UserInfoRepository;
import com.example.capstone_backend.domain.user.dto.request.UserBodySpecEditDTO;
import com.example.capstone_backend.domain.user.entity.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserWriteServiceTest {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserWriteService userWriteService;

    @Test
    void UserBodySpecEditTest(){
        UserInfo userInfo = UserInfo.builder()
                .email("qwer")
                .userName("kkf")
                .userPassword("23984")
                .userProfile("profile")
                .height(169.3d)
                .weight(65d)
                .muscleMass(32.5d)
                .fatMass(9d)
                .sex("남")
                .build();
        userInfoRepository.save(userInfo);

        UserBodySpecEditDTO userBodySpecEditDTO = UserBodySpecEditDTO.builder()
                .height(170.3d)
                .weight(65.5d)
                .muscleMass(33d)
                .fatMass(9d)
                .build();
        System.out.println(userWriteService.userBodySpecEdit(userInfo.getId(), userBodySpecEditDTO));
    }

}