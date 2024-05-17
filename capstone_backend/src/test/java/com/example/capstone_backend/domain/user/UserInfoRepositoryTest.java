package com.example.capstone_backend.domain.user;

import com.example.capstone_backend.domain.user.entity.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class UserInfoRepositoryTest {
    @Autowired
    UserInfoRepository userInfoRepository;

    @Test
    public void insertTest(){
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
                            .sex("남")
                            .build();
                    userInfoRepository.save(userInfo);
                }
        );
        System.out.println(userInfoRepository.userCount("남"));
        System.out.println(userInfoRepository.getBetterBodyScoreUserCount(30.0d, "남"));
        System.out.println(userInfoRepository.getRecommendedUsers(25.0d, "남"));
    }
}