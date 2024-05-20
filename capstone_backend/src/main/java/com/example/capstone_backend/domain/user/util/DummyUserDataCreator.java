package com.example.capstone_backend.domain.user.util;

import com.example.capstone_backend.domain.user.UserInfoRepository;
import com.example.capstone_backend.domain.user.entity.UserInfo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;


@Component
public class DummyUserDataCreator {

    final double bmiMin = 16.5;
    final double bmiMax = 34.5;
    final double manWeightAverage = 75.33;
    final double manFatMassAverage = 22.61;
    final double womanWeightAverage = 59.96;
    final double womanFatMassAverage = 31.52;
    final double muscleMassAverage = 0.45;

    Random random = new Random();

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

    public int[][] getMaleBenchScale() {
        return maleBenchScale;
    }

    public int[][] getMaleSquatScale() {
        return maleSquatScale;
    }

    public int[][] getMaleDeadScale() {
        return maleDeadScale;
    }

    public int[][] getFemaleBenchScale() {
        return femaleBenchScale;
    }

    public int[][] getFemaleSquatScale() {
        return femaleSquatScale;
    }

    public int[][] getFemaleDeadScale() {
        return femaleDeadScale;
    }

    public void createDummy(UserInfoRepository userInfoRepository) {
        IntStream.rangeClosed(1, 100).forEach(
                i -> {
                    double weight = Math.round((50 + (140 - 50) * random.nextDouble()) * 10) / 10.0;
                    double bmi = generateRandomBMI();
                    double fatMass = generateRandomFatMass(weight, "남");
                    UserInfo userInfo = UserInfo.builder()
                            .email("dummy" + i + "@dummy.com")
                            .userName("dummy" + i)
                            .userPassword("1234")
                            .userProfile("profile")
                            .weight(weight)
                            .height(calculateHeight(bmi, weight))
                            .muscleMass(generateRandomMuscleMass(weight))
                            .fatMass(fatMass)
                            .bodyScore(calculateBodyScore(weight, bmi, fatMass, "남"))
                            .BMI(bmi)
                            .sex("남")
                            .build();
                    userInfoRepository.save(userInfo);
                }
        );
    }

    private double calculateMean(double max, double min) {
        return (max + min) / 2.0;
    }

    /**
     * 최소, 최대, 표준편차, 소수점
     *
     * @param mean
     * @param stdDev
     * @param accuracy
     * @return random double
     */
    private double generateRandomValueWithStdDev(double mean, double stdDev, int accuracy) {
        double ret = mean + random.nextGaussian() * stdDev;
        double point = 1;
        for (int i = 0; i < accuracy; i++) {
            point *= 10;
        }
        return Math.round(ret * point) / point;
    }


    // 16.5 ~ 34.5 사이의 임의의 수를 뽑는 함수
    private double generateRandomBMI() {
        return generateRandomValueWithStdDev(calculateMean(bmiMin, bmiMax), 5, 1);
    }

    private double generateRandomFatMass(double weight, String sex) {
        double fatMass = 0.0;
        if (sex == "남") {
            fatMass = weight * generateRandomValueWithStdDev(manFatMassAverage, 5, 2);
        }
        else if (sex == "여") {
            fatMass = generateRandomValueWithStdDev(womanFatMassAverage, 5, 2);
        }
        return fatMass;
    }

    private double generateRandomMuscleMass(double weight) {
        return weight * generateRandomValueWithStdDev(muscleMassAverage, 5, 2);
    }


    // 체중, bmi를 가지고 키를 만들어냅시다. 남자 50~140 여자 40~120
    private double calculateHeight(double bmi, double weight) {
        double height = weight / bmi;

        return Math.round(Math.sqrt(height) * 10) / 10.0;
    }


    private Double calculateBodyScore(double height, double weight, double fatMass, String sex) {

        Double FFM = weight - fatMass;
        Double averageWeight = 0.0;

        Double constantFFM = 0.0;
        Double constantFatPercent = 0.0;

        if (sex == "남") {
            constantFFM = 0.85;
            constantFatPercent = 0.15;
            averageWeight = Math.pow((height / 100), 2) * 22;
        } else if (sex == "여") {
            constantFFM = 0.77;
            constantFatPercent = 0.23;
            averageWeight = Math.pow((height / 100), 2) * 21;
        } else
            throw new IllegalArgumentException("성별을 잘못 입력하셨습니다.");

        Double averageFFM = averageWeight * constantFFM;
        Double averageFatMass = averageWeight * constantFatPercent;

        Double bodyScore = 80 - (averageFFM - FFM) + (averageFatMass - fatMass);
        return bodyScore;
    }

    private void calculateMeanAndStdDev(ArrayList<Double> data) {
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

//    public Exercise exerciseCreator(Long id) {
//        Exercise exercise = Exercise.builder()
//                .exerciseName("벤치프레스")
//                .record(defaultValue * 4 + i)
//                .contents("contents")
//                .userId(userInfoRepository.findById((long) i).get())
//                .build();
//
//        return exercise;
//    }

}
