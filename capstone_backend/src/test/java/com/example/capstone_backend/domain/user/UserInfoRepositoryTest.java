package com.example.capstone_backend.domain.user;

import com.example.capstone_backend.domain.user.entity.UserInfo;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLOutput;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

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
                            .build();
                    userInfoRepository.save(userInfo);
                }
        );
        System.out.println(userInfoRepository.userCount());
        System.out.println(userInfoRepository.getBetterBodyScoreUser(30.0d));
        System.out.println(userInfoRepository.getRecommendedUsers(25.0d));
    }
}