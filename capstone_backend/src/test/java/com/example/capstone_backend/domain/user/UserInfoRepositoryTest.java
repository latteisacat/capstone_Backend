package com.example.capstone_backend.domain.user;

import com.example.capstone_backend.domain.user.entity.UserInfo;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserInfoRepositoryTest {
    @Autowired
    UserInfoRepository userInfoRepository;

    @Test
    public void insertTest(){
        float defaultValue = 23.3f;
        IntStream.rangeClosed(1, 20).forEach(
                i -> {
                    UserInfo userInfo = UserInfo.builder()
                            .email("qwer")
                            .name("kkf")
                            .password("23984")
                            .height(defaultValue + i)
                            .weight(defaultValue + i)
                            .muscleMass(defaultValue + i)
                            .fatMass(defaultValue + i)
                            .build();
                    userInfoRepository.save(userInfo);
                }
        );
    }
}