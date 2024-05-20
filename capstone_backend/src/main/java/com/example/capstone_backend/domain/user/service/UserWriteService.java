package com.example.capstone_backend.domain.user.service;

import com.example.capstone_backend.domain.user.UserInfoRepository;
import com.example.capstone_backend.domain.user.dto.request.UserBodySpecEditDTO;
import com.example.capstone_backend.domain.user.dto.response.UserBodySpecEditResponseDTO;
import com.example.capstone_backend.domain.user.entity.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserWriteService {

    final private UserInfoRepository userInfoRepository;
    public UserBodySpecEditResponseDTO userBodySpecEdit(Long userId, UserBodySpecEditDTO userBodySpecEditDTO){
        UserInfo user = userInfoRepository.findById(userId).orElseThrow();
        String sex = user.getSex();

        user.setHeight(userBodySpecEditDTO.height());
        user.setWeight(userBodySpecEditDTO.weight());
        user.setMuscleMass(userBodySpecEditDTO.muscleMass());
        user.setFatMass(userBodySpecEditDTO.fatMass());

        double bodyScore = calculateBodyScore(userBodySpecEditDTO, sex);
        user.setBodyScore(bodyScore);

        double BMI = calculateBMI(userBodySpecEditDTO.height(), userBodySpecEditDTO.weight());
        user.setBMI(BMI);

        return UserBodySpecEditResponseDTO.builder()
                .userId(user.getId())
                .height(userBodySpecEditDTO.height())
                .weight(userBodySpecEditDTO.weight())
                .muscleMass(userBodySpecEditDTO.muscleMass())
                .bodyFat(userBodySpecEditDTO.fatMass())
                .BMI(BMI)
                .percentageFat(userBodySpecEditDTO.fatMass() / userBodySpecEditDTO.weight() * 100)
                .build();
    }

    public Double calculateBodyScore(UserBodySpecEditDTO userBodySpecEditDTO, String sex){
        Double height = userBodySpecEditDTO.height();
        Double weight = userBodySpecEditDTO.weight();
        Double fatMass = userBodySpecEditDTO.fatMass();

        Double FFM = weight - fatMass;
        Double averageWeight = 0.0;

        Double constantFFM = 0.0;
        Double constantFatPercent = 0.0;
        try {
            if (sex == "남"){
                constantFFM = 0.85;
                constantFatPercent = 0.15;
                averageWeight = Math.pow((height/100),2) * 22;
            }
            else if (sex == "여"){
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

    private Double calculateBMI(Double height, Double weight){
        return weight / Math.pow((height/100),2);
    }
}
