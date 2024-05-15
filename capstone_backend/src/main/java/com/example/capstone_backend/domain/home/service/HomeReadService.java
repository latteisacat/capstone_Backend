package com.example.capstone_backend.domain.home.service;


import com.example.capstone_backend.domain.user.CompetitorRepository;
import com.example.capstone_backend.domain.user.ExerciseRepository;
import com.example.capstone_backend.domain.user.UserInfoRepository;
import com.example.capstone_backend.domain.user.dto.response.UserCompetitorDTO;
import com.example.capstone_backend.domain.user.dto.response.UserHomeResponseDTO;
import com.example.capstone_backend.domain.user.entity.Competitor;
import com.example.capstone_backend.domain.user.entity.Exercise;
import com.example.capstone_backend.domain.user.entity.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HomeReadService {

    final private UserInfoRepository userInfoRepository;
    final private ExerciseRepository exerciseRepository;
    final private CompetitorRepository competitorRepository;

    public UserHomeResponseDTO getUserHome(final Long userId){
        UserInfo user = userInfoRepository.findById(userId).orElseThrow();
        List<Competitor> myCompetitors = competitorRepository.findAllByFromUserId(user);
        List<Exercise> myExerciseList = exerciseRepository.findAllByUser(user);

        String sex = user.getSex();

        Long userCount = userInfoRepository.userCount(sex);
        Double percentageFat = user.getFatMass() / user.getWeight() * 100;
        Long betterBodyScoreUserCount = userInfoRepository.getBetterBodyScoreUser(user.getBodyScore(), sex);
        Double userPercentage = (double) betterBodyScoreUserCount / userCount * 100;

        List<UserHomeResponseDTO.UserRecord> userRecords = getUserRecords(myExerciseList, sex);

        List<UserHomeResponseDTO.AverageRecord> averageRecords = getAverageRecord(myExerciseList, sex);

        List<UserHomeResponseDTO.RecommendedUser> recommendedUsers =
                userInfoRepository.getRecommendedUsers(user.getBodyScore(), sex)
                        .stream().map(UserHomeResponseDTO.RecommendedUser::of).toList();

        List<UserCompetitorDTO> userCompetitorDTOList = getUserCompetitorDTOList(user, myCompetitors);

        return UserHomeResponseDTO.builder()
                .userId(user.getId())
                .name(user.getUserName())
                .sex(user.getSex())
                .profile(user.getUserProfile())
                .height(user.getHeight())
                .weight(user.getWeight())
                .muscleMass(user.getMuscleMass())
                .bodyFat(user.getFatMass())
                .percentageFat(percentageFat)
                .userPercentage(userPercentage+"%")
                .competitors(userCompetitorDTOList)
                .userRecords(userRecords)
                .graph(averageRecords)
                .recommendedUsers(recommendedUsers)
                .build();
    }

    private List<UserCompetitorDTO> getUserCompetitorDTOList(UserInfo user, List<Competitor> myCompetitors) {
        List<UserCompetitorDTO> userCompetitorDTOList = new ArrayList<>();
        for(Competitor competitor: myCompetitors){
            List<List<UserCompetitorDTO.CompareDetail>> userCompare = new ArrayList<>();
            userCompare.add(UserCompetitorDTO.CompareDetail.of("키", user.getHeight(), competitor.getToUserId().getHeight()));
            userCompare.add(UserCompetitorDTO.CompareDetail.of("몸무게", user.getWeight(), competitor.getToUserId().getWeight()));
            userCompare.add(UserCompetitorDTO.CompareDetail.of("근육량", user.getMuscleMass(), competitor.getToUserId().getMuscleMass()));
            userCompare.add(UserCompetitorDTO.CompareDetail.of("체지방량", user.getFatMass(), competitor.getToUserId().getFatMass()));
            userCompare.add(UserCompetitorDTO.CompareDetail.of("BMI", user.getBMI(), competitor.getToUserId().getBMI()));
            userCompare.add(UserCompetitorDTO.CompareDetail.of(
                    "체지방률", user.getFatMass() / user.getWeight() * 100,
                    competitor.getToUserId().getFatMass() / competitor.getToUserId().getWeight() * 100));
            userCompetitorDTOList.add(UserCompetitorDTO.builder()
                            .userId(competitor.getToUserId().getId())
                            .userProfile(competitor.getToUserId().getUserProfile())
                            .userName(competitor.getToUserId().getUserName())
                            .userCompare(userCompare)
                    .build());
        }
        return userCompetitorDTOList;
    }

    private List<UserHomeResponseDTO.AverageRecord> getAverageRecord(List<Exercise> myExerciseList, String sex) {
        List<UserHomeResponseDTO.AverageRecord> averageRecords = new ArrayList<>();
        for(Exercise myExercise : myExerciseList){
            Double averageRecord = exerciseRepository.getAverageRecord(myExercise.getExerciseName(), sex);
            averageRecords.add(UserHomeResponseDTO.AverageRecord.builder()
                    .name(myExercise.getExerciseName())
                    .me(myExercise.getRecord().toString() + "kg")
                    .average(averageRecord.toString() + "kg")
                    .build());
        }
        return averageRecords;
    }

    private List<UserHomeResponseDTO.UserRecord> getUserRecords(List<Exercise> myExerciseList, String sex) {
        // 유저기록을 퍼센트로 쪼개주는 메소드
        List<UserHomeResponseDTO.UserRecord> userRecords = new ArrayList<>();
        for(Exercise exercise : myExerciseList){
            Long betterExerciseUserCount = exerciseRepository.getBetterExerciseUser(exercise.getExerciseName(), exercise.getRecord(), sex);
            Long exerciseUserCount = exerciseRepository.getExerciseUser(exercise.getExerciseName(), sex);
            Double exercisePercentage = (double) betterExerciseUserCount / exerciseUserCount * 100;
            userRecords.add(UserHomeResponseDTO.UserRecord.builder()
                            .sportName(exercise.getExerciseName())
                            .record(exercise.getRecord().toString() + "kg")
                            .percentage(exercisePercentage + "%")
                    .build());
        }
        return userRecords;
    }
}
