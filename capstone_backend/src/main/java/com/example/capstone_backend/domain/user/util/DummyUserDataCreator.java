package com.example.capstone_backend.domain.user.util;

import com.example.capstone_backend.domain.user.ExerciseRepository;
import com.example.capstone_backend.domain.user.UserInfoRepository;
import com.example.capstone_backend.domain.user.entity.Exercise;
import com.example.capstone_backend.domain.user.entity.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Random;


@Component
@RequiredArgsConstructor
public class DummyUserDataCreator {
    static int onOff = 1;
    static int count = 0;

    private final UserInfoRepository userInfoRepository;

    private final ExerciseRepository exerciseRepository;

    // 인바디 자료
    final double bmiMin = 16.5;
    final double bmiMax = 34.5;
    final double manMuscleMassPercentAverage = 42.00;
    final double womanMuscleMassPercentAverage = 35.00;

    final double manWeightAverage = 75.33;
    final double manHeightAverage = 172.12;
    final double manFatMassPercentAverage = 18.61;
//    final double manFatMassPercentAverage = 22.61;

    final double womanWeightAverage = 59.96;
    final double womanHeightAverage = 159.10;
    final double womanFatMassPercentAverage = 25.52;
//    final double womanFatMassPercentAverage = 31.52;


    Random random = new Random();

    public static int getOnOff() {
        return onOff;
    }

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

    private final int[][] maleShoulderScale = {
            {12, 60}, {15, 65}, {17, 71}, {20, 76}, {22, 81},
            {25, 85}, {28, 90}, {30, 94}, {33, 98}, {35, 102},
            {37, 106}, {40, 110}, {41, 113}, {44, 117}, {45, 120},
            {48, 124}, {50, 127}, {52, 131}, {54, 134}
    };

    private final int[][] maleDumbbellScale = {
            {2, 33}, {3, 35}, {4, 37}, {4, 39}, {5, 40},
            {5, 42}, {5, 43}, {6, 45}, {7, 46}, {7, 47},
            {7, 49}, {8, 50}, {8, 51}, {9, 53}, {9, 54},
            {10, 55}, {10, 56}, {10, 57}, {11, 58}
    };

    private final int[][] maleBarbellScale = {
            {9, 64}, {11, 69}, {13, 73}, {14, 78}, {16, 82},
            {18, 85}, {19, 89}, {21, 93}, {23, 96}, {24, 99},
            {26, 102}, {27, 105}, {29, 108}, {30, 111}, {32, 114},
            {33, 116}, {34, 119}, {36, 121}, {37, 124}
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
            {20, 100}, {22, 107}, {26, 113}, {28, 119}, {31, 124},
            {34, 129}, {36, 133}, {38, 138}, {40, 142}, {43, 146},
            {45, 150}, {46, 153}, {49, 157}, {51, 160}, {52, 164},
            {54, 167}, {56, 170}
    };

    private final int[][] femaleShoulderScale = {
            {5, 40}, {6, 44}, {8, 47}, {9, 49}, {10, 51},
            {11, 53}, {12, 55}, {13, 57}, {14, 59}, {15, 61},
            {16, 63}, {17, 65}, {17, 66}, {18, 68}, {19, 69},
            {20, 72}, {20, 73}, {20, 74}
    };

    private final int[][] femaleDumbbellScale = {
            {2, 24}, {2, 25}, {3, 27}, {3, 28}, {3, 29},
            {4, 30}, {4, 31}, {4, 32}, {5, 33}, {5, 34},
            {5, 35}, {6, 35}, {6, 36}, {6, 37}, {7, 38},
            {7, 38}, {7, 39}
    };

    private final int[][] femaleBarbellScale = {
            {3, 41}, {4, 44}, {5, 47}, {5, 49}, {6, 51},
            {7, 54}, {8, 56}, {8, 57}, {9, 59}, {10, 61},
            {10, 63}, {11, 64}, {12, 66}, {12, 67}, {13, 69},
            {14, 70}, {14, 71}
    };

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

    public void dummyOnOff(){
        onOff = 1 - onOff;
    }

    public void createDummy() {
        if(onOff == 1){
            for (int i = 0; i < 100; ++i) {
                generateManDummy(count + i, userInfoRepository, exerciseRepository);
            }

            for (int i = 0; i < 100; ++i) {
                generateWomanDummy(count + i, userInfoRepository, exerciseRepository);
            }
            count += 100;
        }
    }

    private void generateWomanDummy(int i, UserInfoRepository userInfoRepository, ExerciseRepository exerciseRepository) {
        double weight, bmi, fatMass, height, muscleMass;

        do {
            height = generateRandomValueWithStdDev(womanHeightAverage, 15, 1);
            weight = generateRandomWeight(height, "여");
            bmi = calculateBmi(height, weight);
            fatMass = generateRandomFatMass(weight, "여");
            muscleMass = generateRandomMuscleMass(weight, fatMass, "여");
        } while (isOutlier(fatMass, muscleMass, weight, height, bmi, "여"));

        double bodyScore = Math.round(calculateBodyScore(height, weight, fatMass, "여"));
        UserInfo userInfo = UserInfo.builder()
                .email("dummy" + (20000 + i) + "@dummy.com")
                .userName("dummy" + (20000 + i))
                .userPassword("1234")
                .userProfile("profile")
                .weight(weight)
                .height(height)
                .muscleMass(muscleMass)
                .fatMass(fatMass)
                .bodyScore(bodyScore)
                .BMI(bmi)
                .sex("여")
                .isDummy(true)
                .build();
        userInfoRepository.save(userInfo);

        generateRandomExercise(userInfo, "벤치프레스", "여", exerciseRepository);
        generateRandomExercise(userInfo, "데드리프트", "여", exerciseRepository);
        generateRandomExercise(userInfo, "스쿼트", "여", exerciseRepository);
        generateRandomExercise(userInfo, "숄더프레스", "여", exerciseRepository);
        generateRandomExercise(userInfo, "덤벨컬", "여", exerciseRepository);
        generateRandomExercise(userInfo, "바벨컬", "여", exerciseRepository);
    }

    private void generateManDummy(int i, UserInfoRepository userInfoRepository, ExerciseRepository exerciseRepository) {
        double weight, bmi, fatMass, height, muscleMass;

        do {
            height = generateRandomValueWithStdDev(manHeightAverage, 16, 1);
            weight = generateRandomWeight(height, "남");
            bmi = calculateBmi(height, weight);
            fatMass = generateRandomFatMass(weight, "남");
            muscleMass = generateRandomMuscleMass(weight, fatMass, "남");
        } while (isOutlier(fatMass, muscleMass, weight, height, bmi, "남"));

        double bodyScore = Math.round(calculateBodyScore(height, weight, fatMass, "남"));
        UserInfo userInfo = UserInfo.builder()
                .email("dummy" + (10000 + i) + "@dummy.com")
                .userName("dummy" + (10000 + i))
                .userPassword("1234")
                .userProfile("profile")
                .weight(weight)
                .height(height)
                .muscleMass(muscleMass)
                .fatMass(fatMass)
                .bodyScore(bodyScore)
                .BMI(bmi)
                .sex("남")
                .isDummy(true)
                .build();

        userInfoRepository.save(userInfo);
        generateRandomExercise(userInfo, "벤치프레스", "남", exerciseRepository);
        generateRandomExercise(userInfo, "데드리프트", "남", exerciseRepository);
        generateRandomExercise(userInfo, "스쿼트", "남", exerciseRepository);
        generateRandomExercise(userInfo, "숄더프레스", "남", exerciseRepository);
        generateRandomExercise(userInfo, "덤벨컬", "남", exerciseRepository);
        generateRandomExercise(userInfo, "바벨컬", "남", exerciseRepository);
    }

    private void generateRandomExercise(UserInfo userInfo, String exName, String sex, ExerciseRepository exerciseRepository) {

        int stdDev = 1;
        if (sex == "남") {
            int weight = (int) (userInfo.getWeight() / 5) - 10; // 몸무게별 구간갯수가 10개임
            double musclePer = userInfo.getMuscleMass() / userInfo.getWeight();

            double dev = musclePer * 100 - manMuscleMassPercentAverage;
            double record = 0;
            switch (exName) {
                case "벤치프레스":
                    record = generateRandomValueWithStdDev(scale(maleBenchScale[weight][0], maleBenchScale[weight][1],
                            dev * 10), stdDev, 1);
                    break;
                case "데드리프트":
                    record = generateRandomValueWithStdDev(scale(maleDeadScale[weight][0], maleDeadScale[weight][1],
                            dev * 10), stdDev, 1);
                    break;
                case "스쿼트":
                    record = generateRandomValueWithStdDev(scale(maleSquatScale[weight][0], maleSquatScale[weight][1],
                            dev * 10), stdDev, 1);
                    break;
                case "숄더프레스":
                    record = generateRandomValueWithStdDev(scale(maleShoulderScale[weight][0], maleShoulderScale[weight][1],
                            dev * 10), stdDev, 1);
                    break;
                case "덤벨컬":
                    record = generateRandomValueWithStdDev(scale(maleDumbbellScale[weight][0], maleDumbbellScale[weight][1],
                            dev * 10), stdDev, 1);
                    break;
                case "바벨컬":
                    record = generateRandomValueWithStdDev(scale(maleBarbellScale[weight][0], maleBarbellScale[weight][1],
                            dev * 10), stdDev, 1);
                    break;
            }
            if (record < 20) {
                record = 20;
            }
            Exercise exercise = Exercise.builder()
                    .userId(userInfo)
                    .exerciseName(exName)
                    .record(record)
                    .build();
            exerciseRepository.save(exercise);

        } else {
            int weight = (int) (userInfo.getWeight() / 5) - 8; // 몸무게별 구간갯수가 8개임
            double musclePer = userInfo.getMuscleMass() / userInfo.getWeight();

            double dev = musclePer * 100 - womanMuscleMassPercentAverage;
            double record = 0;
            switch (exName) {
                case "벤치프레스":
                    record = generateRandomValueWithStdDev(scale(femaleBenchScale[weight][0], femaleBenchScale[weight][1],
                            dev * 10), stdDev, 1);
                    break;
                case "데드리프트":
                    record = generateRandomValueWithStdDev(scale(femaleDeadScale[weight][0], femaleDeadScale[weight][1],
                            dev * 10), stdDev, 1);
                    break;
                case "스쿼트":
                    record = generateRandomValueWithStdDev(scale(femaleSquatScale[weight][0], femaleSquatScale[weight][1],
                            dev * 10), stdDev, 1);
                    break;
                case "숄더프레스":
                    record = generateRandomValueWithStdDev(scale(femaleShoulderScale[weight][0], femaleShoulderScale[weight][1],
                            dev * 10), stdDev, 1);
                    break;
                case "덤벨컬":
                    record = generateRandomValueWithStdDev(scale(femaleDumbbellScale[weight][0], femaleDumbbellScale[weight][1],
                            dev * 10), stdDev, 1);
                    break;
                case "바벨컬":
                    record = generateRandomValueWithStdDev(scale(femaleBarbellScale[weight][0], femaleBarbellScale[weight][1],
                            dev * 10), stdDev, 1);
                    break;
            }

            if (record < 10) {
                record = 10;
            }

            Exercise exercise = Exercise.builder()
                    .userId(userInfo)
                    .exerciseName(exName)
                    .record(record)
                    .build();
            exerciseRepository.save(exercise);

        }
    }

    double scale(int min, int max, double percent) {
        if (percent <= 0) {
            return min;
        } else if (percent >= 100) {
            return max;
        } else {
            double range = max - min;
            return min + (range * percent / 100);
        }
    }


    private double generateRandomFatMass(double weight, String sex) {
        double fatMass = 0.0;
        if (sex == "남") {
            fatMass = weight * generateRandomValueWithStdDev(manFatMassPercentAverage, 5, 2) / 100;
        } else if (sex == "여") {
            fatMass = weight * generateRandomValueWithStdDev(womanFatMassPercentAverage, 6, 2) / 100;
        }
        return Math.round(fatMass * 10.0) / 10.0;
    }

    private double generateRandomMuscleMass(double weight, double fatmass, String sex) {
        // 낮은 체지방 기준
        double lowFat = 17;

        // 체지방이 낮으면 가산점
        double point = scale(0, 8, (lowFat - (fatmass / weight) * 100) * 10);

        if (point == 0) {
            if(sex == "남"){
                weight = weight * generateRandomValueWithStdDev(manMuscleMassPercentAverage, 4, 2) / 100;
            }
            else{
                weight = weight * generateRandomValueWithStdDev(womanMuscleMassPercentAverage, 4, 2) / 100;
            }
        } else {
            if (sex == "남"){
                weight = weight * generateRandomValueWithStdDev(manMuscleMassPercentAverage + point, 4, 2) / 100;
            }
            else{
                weight = weight * generateRandomValueWithStdDev(womanMuscleMassPercentAverage + point, 4, 2) / 100;
            }
        }

        return Math.round(weight * 100.0) / 100.0;
    }

    private double generateRandomWeight(double height, String sex) {
        double dev = 0;
        switch (sex) {
            case "남":
                dev = height - manHeightAverage;
                return generateRandomValueWithStdDev(manWeightAverage + dev, 13, 1);
            case "여":
                dev = height - womanHeightAverage;
                return generateRandomValueWithStdDev(womanWeightAverage + dev, 9, 1);
        }
        return 0;
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

    // 체중, bmi를 가지고 키를 만들어냅시다. 남자 50~140 여자 40~120
    private double calculateBmi(double height, double weight) {
        return Math.round(weight / Math.pow((height / 100), 2) * 10.0) / 10.0;
    }

    // 이상치
    // fatmass musclemass 합쳐서 weight over
    // 골격근 45~50 체지방 20~30 최대 85정도
    // 키가 너무 작은 경우 키가 너무 큰 경우
    // bmi가 최대 최소를 넘어가는 경우
    // 체지방률이나 골격근량이 너무너무너무너무 낮거나 큰경우
    private boolean isOutlier(double fatMass, double muscleMass, double weight, double height, double bmi, String
            str) {
        switch (str) {
            case "남":
                if (fatMass + muscleMass >= weight || fatMass + muscleMass <= weight * 0.65 || fatMass + muscleMass >= weight * 0.85) {
                    return true;
                } else if (weight < 50 || weight > 140) {
                    return true;
                } else if (height <= 150 || height > 200) {
                    return true;
                } else if (bmi < bmiMin || bmi > bmiMax) {
                    return true;
                }
                break;
            case "여":
                if (fatMass + muscleMass >= weight || fatMass + muscleMass <= weight * 0.65 || fatMass + muscleMass >= weight * 0.85) {
                    return true;
                } else if (weight < 40 || weight > 120) {
                    return true;
                } else if (height <= 140 || height > 195) {
                    return true;
                } else if (bmi < bmiMin || bmi > bmiMax) {
                    return true;
                }
                break;
        }

        return false;
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
}
