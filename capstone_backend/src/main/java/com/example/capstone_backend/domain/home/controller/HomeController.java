package com.example.capstone_backend.domain.home.controller;


import com.example.capstone_backend.domain.user.dto.response.UserCompetitorDTO;
import com.example.capstone_backend.domain.user.dto.response.UserHomeResponseDTO;
import com.example.capstone_backend.domain.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/home")
public class HomeController {

    @GetMapping("/{userId}")
    public ResponseEntity<?> userHome(@PathVariable("userId") final Integer userId){
        return ResponseEntity.ok(ApiUtils.success(dummy()));
    }

    private UserHomeResponseDTO dummy(){
        UserHomeResponseDTO userHomeResponseDTO;

        List<UserCompetitorDTO> competitors = new ArrayList<>();
        List<List<UserCompetitorDTO.CompareDetail>> compareDetails = new ArrayList<>();
        List<UserHomeResponseDTO.UserRecord> userRecords = new ArrayList<>();
        List<UserHomeResponseDTO.RecommendedUser> recommendedUsers = new ArrayList<>();

        for(int i = 0; i < 6; i++){
            compareDetails.add(new ArrayList<UserCompetitorDTO.CompareDetail>());
        }

        compareDetails.get(0).add(UserCompetitorDTO.CompareDetail.builder()
                .name("키")
                .me(169.0)
                .competitor(174.0)
                .build());
        compareDetails.get(1).add(UserCompetitorDTO.CompareDetail.builder()
                .name("몸무게")
                .me(169.0)
                .competitor(174.0)
                .build());
        compareDetails.get(2).add(UserCompetitorDTO.CompareDetail.builder()
                .name("근육량")
                .me(169.0)
                .competitor(174.0)
                .build());
        compareDetails.get(3).add(UserCompetitorDTO.CompareDetail.builder()
                .name("체지방량")
                .me(169.0)
                .competitor(174.0)
                .build());
        compareDetails.get(4).add(UserCompetitorDTO.CompareDetail.builder()
                .name("BMI")
                .me(169.0)
                .competitor(174.0)
                .build());
        compareDetails.get(5).add(UserCompetitorDTO.CompareDetail.builder()
                .name("체지방률")
                .me(169.0)
                .competitor(174.0)
                .build());

        competitors.add(UserCompetitorDTO.builder()
                .userId(1L)
                .userProfile("safsaf")
                .userName("asfasdfsd")
                .userCompare(compareDetails)
                .build());
        competitors.add(UserCompetitorDTO.builder()
                .userId(2L)
                .userProfile("safsaf")
                .userName("asfasdfsd")
                .userCompare(compareDetails)
                .build());
        competitors.add(UserCompetitorDTO.builder()
                .userId(3L)
                .userProfile("safsaf")
                .userName("asfasdfsd")
                .userCompare(compareDetails)
                .build());

        userRecords.add(UserHomeResponseDTO.UserRecord.builder()
                .sportName("데드리프트")
                .record("160kg")
                .percentage("30%")
                .build());
        userRecords.add(UserHomeResponseDTO.UserRecord.builder()
                .sportName("벤치프레스")
                .record("100kg")
                .percentage("30%")
                .build());
        userRecords.add(UserHomeResponseDTO.UserRecord.builder()
                .sportName("스쿼트")
                .record("130kg")
                .percentage("30%")
                .build());

        recommendedUsers.add(UserHomeResponseDTO.RecommendedUser.builder()
                .userId(6l)
                .name("sfgsfdg")
                .profile("345435afads")
                .height(176.0)
                .weight(69.0)
                .build());
        recommendedUsers.add(UserHomeResponseDTO.RecommendedUser.builder()
                .userId(7l)
                .name("sfgsfdg")
                .profile("345435afads")
                .height(176.0)
                .weight(69.0)
                .build());
        recommendedUsers.add(UserHomeResponseDTO.RecommendedUser.builder()
                .userId(8l)
                .name("sfgsfdg")
                .profile("345435afads")
                .height(176.0)
                .weight(69.0)
                .build());
        recommendedUsers.add(UserHomeResponseDTO.RecommendedUser.builder()
                .userId(9l)
                .name("sfgsfdg")
                .profile("345435afads")
                .height(176.0)
                .weight(69.0)
                .build());
        recommendedUsers.add(UserHomeResponseDTO.RecommendedUser.builder()
                .userId(10l)
                .name("sfgsfdg")
                .profile("345435afads")
                .height(176.0)
                .weight(69.0)
                .build());
        recommendedUsers.add(UserHomeResponseDTO.RecommendedUser.builder()
                .userId(11l)
                .name("sfgsfdg")
                .profile("345435afads")
                .height(176.0)
                .weight(69.0)
                .build());


        userHomeResponseDTO = UserHomeResponseDTO.builder()
                .userId(234l)
                .name("23423423")
                .profile("oaidfoijasf")
                .height(169.0)
                .weight(65.4)
                .muscleMass(32.5)
                .bodyFat(9.5)
                .percentageFat(14.5)
                .userPercentage("20%")
                .competitors(competitors)
                .userRecords(userRecords)
                .recommendedUsers(recommendedUsers)
                .build();
        return userHomeResponseDTO;
    }
}
