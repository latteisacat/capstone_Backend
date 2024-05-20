package com.example.capstone_backend.domain.user.util;

import com.example.capstone_backend.domain.user.ExerciseRepository;
import com.example.capstone_backend.domain.user.UserInfoRepository;
import com.example.capstone_backend.domain.user.entity.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;
import java.util.stream.IntStream;


@SpringBootTest
public class DummyTest {

    public DummyUserDataCreator dummy = new DummyUserDataCreator();

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    ExerciseRepository exerciseRepository;

    @Test
    public void reverseBMICalc(){
        double weight = 55;
        double bmi = 19.4;
        double height = weight / bmi;
        System.out.println(Math.round(Math.sqrt(height) * 100) / 100.0);
//        return Math.round(Math.sqrt(height) * 10) / 10.0;

        // 16.5 ~ 33.3 사이의 임의의 수를 뽑는 함수
//        public double generateRandomBMI() {
            Random random = new Random();
//            return Math.round((16.5 + (33.3 - 16.5) * random.nextDouble()) * 100) / 100.0;
        System.out.println(Math.round((16.5 + (33.3 - 16.5) * random.nextDouble()) * 10) / 10.0);
        System.out.println(Math.round((16.5 + (33.3 - 16.5) * random.nextDouble()) * 10) / 10.0);
        System.out.println(Math.round((16.5 + (33.3 - 16.5) * random.nextDouble()) * 10) / 10.0);
        System.out.println(Math.round((16.5 + (33.3 - 16.5) * random.nextDouble()) * 10) / 10.0);
        System.out.println(Math.round((16.5 + (33.3 - 16.5) * random.nextDouble()) * 10) / 10.0);
        System.out.println(Math.round((16.5 + (33.3 - 16.5) * random.nextDouble()) * 10) / 10.0);
//        }
    }

//    @BeforeEach
    void input(){
        double defaultValue = 23.3d;
        IntStream.rangeClosed(1, 100).forEach(
                i -> {
                    UserInfo userInfo = UserInfo.builder()
                            .email("dummy" + i)
                            .userName("dummy")
                            .userPassword("1234")
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

        IntStream.rangeClosed(101, 200).forEach(
                i -> {
                    UserInfo userInfo = UserInfo.builder()
                            .email("dummy" + i)
                            .userName("dummy")
                            .userPassword("1234")
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
    public void insertTest() {
        dummy.createDummy(userInfoRepository);
    }

}