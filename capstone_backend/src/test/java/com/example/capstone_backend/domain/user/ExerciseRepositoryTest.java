package com.example.capstone_backend.domain.user;

import com.example.capstone_backend.domain.user.entity.Exercise;
import com.example.capstone_backend.domain.user.entity.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ExerciseRepositoryTest {
    @Autowired
    ExerciseRepository exerciseRepository;

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
        IntStream.rangeClosed(1, 20).forEach(
                i -> {
                    Exercise exercise1 = Exercise.builder()
                            .exerciseName("데드리프트")
                            .record(defaultValue * 6 + i)
                            .contents("contents")
                            .userId(userInfoRepository.findById((long) i).get())
                            .build();
                    Exercise exercise2 = Exercise.builder()
                            .exerciseName("스쿼트")
                            .record(defaultValue * 6 + i)
                            .contents("contents")
                            .userId(userInfoRepository.findById((long) i).get())
                            .build();
                    Exercise exercise3 = Exercise.builder()
                            .exerciseName("벤치프레스")
                            .record(defaultValue * 4 + i)
                            .contents("contents")
                            .userId(userInfoRepository.findById((long) i).get())
                            .build();
                    exerciseRepository.save(exercise1);
                    exerciseRepository.save(exercise2);
                    exerciseRepository.save(exercise3);
                }
        );
    }

    @Test
    void exerciseRepositoryTest(){
        System.out.println(exerciseRepository.getBetterExerciseUserCount("데드리프트", 30.0d, "남"));
        System.out.println(exerciseRepository.getExerciseUserCount("데드리프트", "남"));
        System.out.println(exerciseRepository.getAverageRecord("데드리프트", "남"));
    }

}