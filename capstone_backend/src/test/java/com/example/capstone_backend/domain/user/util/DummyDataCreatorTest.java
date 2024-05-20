package com.example.capstone_backend.domain.user.util;

import com.example.capstone_backend.domain.user.UserInfoRepository;
import com.example.capstone_backend.domain.user.entity.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.capstone_backend.domain.user.util.DummyDataCreator;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;


@SpringBootTest
public class DummyDataCreatorTest {

    DummyDataCreator dummyDataCreator = new DummyDataCreator();


    @Autowired
    UserInfoRepository userInfoRepository;

    // male weight 50 ~ 140
    private final int[][] maleBenchScale = {
            {20, 87}, {24, 96}, {28, 104}, {33, 112}, {37, 120},
            {41, 126}, {45, 133}, {49, 140}, {52, 146}, {56, 153},
            {60, 159}, {63, 164}, {68, 170}, {71, 176}, {74, 181},
            {78, 186}, {80, 191}, {84, 196}, {87, 200}
    };

    private final int[][] maleSquatScale = {
            {28, 115}, {34, 126}, {39, 137}, {45, 147}, {50, 156},
            {56, 165}, {61, 176}, {67, 184}, {72, 193}, {76, 202},
            {81, 211}, {85, 219}, {90, 227}, {94, 236}, {97, 244},
            {102, 252}, {107, 258}, {111, 267}, {115, 275}
    };

    private final int[][] maleDeadScale = {
            {37, 136}, {43, 147}, {49, 158}, {56, 170}, {62, 180},
            {67, 190}, {73, 199}, {79, 208}, {84, 217}, {89, 226},
            {94, 233}, {99, 241}, {104, 249}, {109, 256}, {114, 264},
            {119, 271}, {124, 277}, {129, 284}, {134, 291}
    };

    // female start from 40 ~ 120
    private final int[][] femaleBenchScale = {
            {6, 59}, {8, 64}, {10, 69}, {12, 73}, {14, 78},
            {16, 81}, {17, 85}, {18, 89}, {20, 92}, {22, 95},
            {23, 98}, {24, 101}, {26, 104}, {28, 107}, {28, 109},
            {30, 112}, {31, 114}
    };

    private final int[][] femaleSquatScale = {
            {14, 86}, {17, 92}, {19, 97}, {22, 103}, {24, 109},
            {27, 113}, {29, 118}, {31, 122}, {33, 126}, {34, 129},
            {37, 133}, {39, 137}, {40, 140}, {42, 146}, {44, 150},
            {46, 153}
    };

    private final int[][] femaleDeadScale = {
            {37, 136}, {43, 147}, {49, 158}, {56, 170}, {62, 180},
            {67, 190}, {73, 199}, {79, 208}, {84, 217}, {89, 226},
            {94, 233}, {99, 241}, {104, 249}, {109, 256}, {114, 264},
            {119, 271}, {124, 277}, {129, 284}, {134, 291}
    };

    public double calculateMean(double max, double min) {
        return (max + min) / 2.0;
    }

    // 주어진 표준편차에 따라 분포하는 값을 추출
    public double generateValueWithStdDev(int min, int max, double stdDev) {
        int mean = (max + min) / 2;
        Random random = new Random();
        double ret = mean + random.nextGaussian() * stdDev;
        // ret값을 소수점2자리까지 반올림
        return Math.round(ret * 100) / 100.0;
    }

    public void calculateMeanAndStdDev(ArrayList<Double> data) {
        double sum = 0;
        for (Double value : data) {
            sum += value;
        }

        double mean = sum / data.size();
        double sumSquaredDiff = 0;
        for (double value : data) {
            double diff = value - mean;
            sumSquaredDiff += diff * diff;
        }
        double meanSquaredDiff = sumSquaredDiff / data.size();
        System.out.println("mean = " + mean);
        System.out.println("meanSquaredDiff = " + Math.sqrt(meanSquaredDiff));
    }

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

    @Test
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

}