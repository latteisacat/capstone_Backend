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

        // 이미 등록된 경쟁자를 리스트에서 제외하기 위함.
        List<Long> competitorIds = myCompetitors.stream().map(competitor -> competitor.getToUserId().getId()).toList();
        String sex = user.getSex();

        Long userCount = userInfoRepository.userCount(sex);
        Double percentageFat;
        Double userPercentage;
        if (user.getFatMass() != null){
            percentageFat = user.getFatMass() / user.getWeight() * 100;
        }
        else{
            percentageFat = null;
        }

        if (user.getBodyScore() != null){
            Long betterBodyScoreUserCount = userInfoRepository.getBetterBodyScoreUserCount(user.getBodyScore(), sex);
            userPercentage = (double) betterBodyScoreUserCount / userCount * 100;
        }
        else{
            userPercentage = null;
        }


        List<UserHomeResponseDTO.UserRecord> userRecords = getUserRecords(myExerciseList, sex);

        List<UserHomeResponseDTO.AverageRecord> averageRecords = getAverageRecord(myExerciseList, sex);

        Double range = 0.15;
        Integer count = 0;
        List<UserHomeResponseDTO.RecommendedUser> recommendedUsers = new ArrayList<>();
        if (userRecords.size() != 0){
            do{
                count += 1;
                recommendedUsers = userInfoRepository.getRecommendedUsers(user.getBodyScore(), sex, user.getId(), range)
                        .stream().map(UserHomeResponseDTO.RecommendedUser::of).toList();
                recommendedUsers = recommendedUsers.stream().filter(recommendedUser -> !competitorIds.contains(recommendedUser.userId())).toList();
                System.out.println("사이즈 : " + recommendedUsers.size());
                range += 0.05;
            }while(recommendedUsers.size() == 0 && count < 5);
        }

        List<UserCompetitorDTO> userCompetitorDTOList = getUserCompetitorDTOList(user, myCompetitors);
        List<UserHomeResponseDTO.NullCompetitor> nullCompetitors = nullCompetitorList(user);


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
                .nullGraph(nullCompetitors)
                .recommendedUsers(recommendedUsers)
                .build();
    }

    private List<UserHomeResponseDTO.NullCompetitor> nullCompetitorList(UserInfo user) {
        List<UserHomeResponseDTO.NullCompetitor> nullCompetitors = new ArrayList<>();
        nullCompetitors.add(UserHomeResponseDTO.NullCompetitor.builder()
                .name("키")
                .me(user.getHeight())
                .competitor(0.0)
                .build());
        nullCompetitors.add(UserHomeResponseDTO.NullCompetitor.builder()
                .name("몸무게")
                .me(user.getWeight())
                .competitor(0.0)
                .build());
        nullCompetitors.add(UserHomeResponseDTO.NullCompetitor.builder()
                .name("근육량")
                .me(user.getMuscleMass())
                .competitor(0.0)
                .build());
        nullCompetitors.add(UserHomeResponseDTO.NullCompetitor.builder()
                .name("체지방량")
                .me(user.getFatMass())
                .competitor(0.0)
                .build());
        nullCompetitors.add(UserHomeResponseDTO.NullCompetitor.builder()
                .name("BMI")
                .me(user.getBMI())
                .competitor(0.0)
                .build());
        nullCompetitors.add(UserHomeResponseDTO.NullCompetitor.builder()
                .name("체지방률")
                .me(user.getFatMass() / user.getWeight() * 100)
                .competitor(0.0)
                .build());
        return nullCompetitors;
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
            Long betterExerciseUserCount = exerciseRepository.getBetterExerciseUserCount(exercise.getExerciseName(), exercise.getRecord(), sex);
            Long exerciseUserCount = exerciseRepository.getExerciseUserCount(exercise.getExerciseName(), sex);
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
