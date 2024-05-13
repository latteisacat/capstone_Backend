package com.example.capstone_backend.domain.user.service;

import com.example.capstone_backend.domain.fileserver.ContentsRepository;
import com.example.capstone_backend.domain.fileserver.entity.Contents;
import com.example.capstone_backend.domain.user.UserInfoRepository;
import com.example.capstone_backend.domain.user.entity.UserInfo;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


@SpringBootTest
public class UserReadServiceTest {

    @Autowired
    private UserReadService userReadService;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private ContentsRepository contentsRepository;

    @Test
    void getUserProfileRequestTest() {
        UserInfo userInfo = UserInfo.builder()
                .email("qwer")
                .userName("kkf")
                .userPassword("23984")
                .userProfile("profile")
                .height(23.3d)
                .weight(23.3d)
                .muscleMass(23.3d)
                .fatMass(23.3d)
                .build();
        userInfoRepository.save(userInfo);
        for(int i = 0; i < 10; i++){
            contentsRepository.save(Contents.builder()
                    .userId(userInfo)
                    .contents("contents")
                    .datatype("datatype")
                    .thumbnail("thumbnail")
                    .build());
        }
        System.out.println(userReadService.getUserProfileRequest(userInfo.getId()));
    }


}