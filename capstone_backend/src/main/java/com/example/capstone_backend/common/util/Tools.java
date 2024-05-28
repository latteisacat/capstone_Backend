package com.example.capstone_backend.common.util;

import com.example.capstone_backend.common.jwt.CustomUserDetails;
import com.example.capstone_backend.domain.user.ExerciseRepository;
import com.example.capstone_backend.domain.user.dto.request.UserBodySpecEditDTO;
import com.example.capstone_backend.domain.user.entity.Exercise;
import com.example.capstone_backend.domain.user.entity.UserInfo;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class Tools {

    public static Double parsingRecord(String record){
        //TODO: kg 외에 달리기 같은 운동을 위해 km/h같은 단위 추가 할 것
        int index = record.indexOf("kg");
        if (index == -1){
            throw new IllegalArgumentException("record must contain 'kg' string");
        }
        else{
            return Double.parseDouble(record.substring(0, index));
        }
    }

    public static Double calculateBodyScore(UserBodySpecEditDTO userBodySpecEditDTO, String sex){
        Double height = userBodySpecEditDTO.height();
        Double weight = userBodySpecEditDTO.weight();
        Double fatMass = userBodySpecEditDTO.fatMass();

        Double FFM = weight - fatMass;
        Double averageWeight = 0.0;

        Double constantFFM = 0.0;
        Double constantFatPercent = 0.0;
        try {
            if (Objects.equals(sex, "남")){
                constantFFM = 0.85;
                constantFatPercent = 0.15;
                averageWeight = Math.pow((height/100),2) * 22;
            }
            else if (Objects.equals(sex, "여")){
                constantFFM = 0.77;
                constantFatPercent = 0.23;
                averageWeight = Math.pow((height/100),2) * 21;
            }
            else
                throw new IllegalArgumentException("성별을 잘못 입력하셨습니다.");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        Double averageFFM = averageWeight * constantFFM;
        Double averageFatMass = averageWeight * constantFatPercent;

        Double bodyScore = 80 - (averageFFM - FFM) + (averageFatMass - fatMass);
        return bodyScore;
    }

    public static Double calculateBMI(Double height, Double weight){
        return weight / Math.pow((height/100),2);
    }

    public static void invalidUserCheck(CustomUserDetails userDetails, Long userId){
        if (userDetails == null){
            throw new IllegalArgumentException("user not found with userId: " + userId + "or token is null");
        }
        else if(userDetails.getUserInfo().getId() != userId){
            throw new IllegalArgumentException("user not matched with userId: " + userId);
        }
    }

    public static String getFileNameFromUrl(final String url) {
        final String[] splitUrl = url.split("/");
        return splitUrl[splitUrl.length - 1];
    }

    public static String thumbnailUrl(String url) {
        String[] parts = url.split("/");
        String filename = parts[parts.length - 1];
        parts[parts.length - 1] = "thumbnails/" + filename + ".thumbnail";
        return String.join("/", parts);
    }
}
