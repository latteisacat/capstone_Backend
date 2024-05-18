package com.example.capstone_backend.domain.home.service;

import com.example.capstone_backend.domain.user.CompetitorRepository;
import com.example.capstone_backend.domain.user.ExerciseRepository;
import com.example.capstone_backend.domain.user.UserInfoRepository;
import com.example.capstone_backend.domain.user.entity.Competitor;
import com.example.capstone_backend.domain.user.entity.Exercise;
import com.example.capstone_backend.domain.user.entity.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class HomeReadServiceTest {

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    CompetitorRepository competitorRepository;

    @Autowired
    HomeReadService homeReadService;

    @BeforeEach
    void setUp(){
        Double defaultValue = 23.3d;
        Double defaultHeight = 165.0d;
        Double defaultWeight = 60.0d;
        Double defaultMuscleMass = 25.0d;
        Double defaultFatMass = 10.0d;
        IntStream.rangeClosed(1, 20).forEach(
                i -> {
                    UserInfo userInfo = UserInfo.builder()
                            .email("qwer" + i)
                            .userName("kkf")
                            .userPassword("23984")
                            .userProfile("profile")
                            .height(defaultHeight + i)
                            .weight(defaultWeight + i)
                            .muscleMass(defaultMuscleMass+ i)
                            .fatMass(defaultFatMass + i)
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
        IntStream.rangeClosed(1, 4).forEach(
                i -> {
                    UserInfo userInfo = userInfoRepository.findById((long) 1).get();
                    UserInfo other = userInfoRepository.findById((long) i + 1).get();
                    Competitor competitor = Competitor.builder()
                            .fromUserId(userInfo)
                            .toUserId(other)
                            .build();
                    competitorRepository.save(competitor);
                }
        );
    }

    @Test
    void homeReadServiceTest(){
        System.out.println(homeReadService.getUserHome(1L));
    }
}