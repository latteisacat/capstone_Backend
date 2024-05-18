package com.example.capstone_backend.domain.user;

import com.example.capstone_backend.domain.user.entity.Exercise;
import com.example.capstone_backend.domain.user.entity.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class UserInfoRepositoryTest {
    @Autowired
    UserInfoRepository userInfoRepository;

    @BeforeEach
    void setUp(){
        Double defaultValue = 23.3d;
        IntStream.rangeClosed(1, 20).forEach(
                i -> {
                    UserInfo userInfo = UserInfo.builder()
                            .email("qwer" + i)
                            .userName("kkf")
                            .userPassword("23984")
                            .userProfile("profile")
                            .height(defaultValue + i)
                            .weight(defaultValue + i)
                            .muscleMass(defaultValue + i)
                            .fatMass(defaultValue + i)
                            .bodyScore(defaultValue + i)
                            .BMI(defaultValue + i)
                            .sex("남")
                            .build();
                    userInfoRepository.save(userInfo);
                }
        );
    }
    @Test
    public void insertTest(){
        System.out.println(userInfoRepository.userCount("남"));
        System.out.println(userInfoRepository.getBetterBodyScoreUserCount(30.0d, "남"));
        System.out.println(userInfoRepository.getRecommendedUsers(28.0d, "남", 1l).get(0).getUserName());
    }
}