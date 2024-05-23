package com.example.capstone_backend.domain.user.service;

import com.example.capstone_backend.common.util.Tools;
import com.example.capstone_backend.domain.fileserver.service.FileValidator;
import com.example.capstone_backend.domain.fileserver.service.FileWriteServiceTransactionManager;
import com.example.capstone_backend.domain.user.ExerciseRepository;
import com.example.capstone_backend.domain.user.UserInfoRepository;
import com.example.capstone_backend.domain.user.dto.request.UserBodySpecEditDTO;
import com.example.capstone_backend.domain.user.dto.request.UserRecordEditDTO;
import com.example.capstone_backend.domain.user.dto.response.UserBodySpecEditResponseDTO;
import com.example.capstone_backend.domain.user.dto.response.UserProfileEditResponseDTO;
import com.example.capstone_backend.domain.user.dto.response.UserRecordEditResponseDTO;
import com.example.capstone_backend.domain.user.entity.Exercise;
import com.example.capstone_backend.domain.user.entity.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Transactional
public class UserWriteService {

    final private UserInfoRepository userInfoRepository;

    final private FileValidator fileValidator;

    final private FileWriteServiceTransactionManager transactionManager;

    final private ExerciseRepository exerciseRepository;
    public UserBodySpecEditResponseDTO userBodySpecEdit(Long userId, UserBodySpecEditDTO userBodySpecEditDTO){
        UserInfo user = userInfoRepository.findById(userId).orElseThrow();
        String sex = user.getSex();

        user.setHeight(userBodySpecEditDTO.height());
        user.setWeight(userBodySpecEditDTO.weight());
        user.setMuscleMass(userBodySpecEditDTO.muscleMass());
        user.setFatMass(userBodySpecEditDTO.fatMass());

        double bodyScore = Tools.calculateBodyScore(userBodySpecEditDTO, sex);
        user.setBodyScore(bodyScore);

        double BMI = Tools.calculateBMI(userBodySpecEditDTO.height(), userBodySpecEditDTO.weight());
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

    public UserRecordEditResponseDTO userRecordEdit(
            final Long userId,
            final UserRecordEditDTO userRecordEditDTO,
            final MultipartFile video,
            final UserInfo userInfo
            ){
        List<Exercise> userExercises;
        final Double record = Tools.parsingRecord(userRecordEditDTO.record());
        if(video != null){
            fileValidator.validateVideoFile(video);
            userExercises = transactionManager
                    .doExerciseRecordUploadTransaction(userRecordEditDTO, video, userInfo);
        }
        else{
            userExercises = exerciseRepository.findAllByUser(userInfo);
            OptionalInt indexOpt = IntStream.range(0, userExercises.size())
                    .filter(i -> userExercises.get(i).getExerciseName().equals(userRecordEditDTO.exerciseName()))
                    .findFirst();
            int index = indexOpt.orElse(-1);
            if(index == -1){
                exerciseRepository.save(
                        Exercise.builder()
                                .userId(userInfo)
                                .exerciseName(userRecordEditDTO.exerciseName())
                                .record(record)
                                .build()
                );
            }
            else{
                userExercises.get(index).setRecord(record);
            }
        }
        List<Double> percentages = userExercises.stream()
                .map(exercise -> {
                    Long exerciseUserCount = exerciseRepository.getExerciseUserCount(
                            exercise.getExerciseName(), userInfo.getSex()
                    );
                    Long betterExerciseUserCount = exerciseRepository.getBetterExerciseUserCount(
                            exercise.getExerciseName(), exercise.getRecord(), userInfo.getSex()
                    );
                    return (double) betterExerciseUserCount / exerciseUserCount * 100;
                })
                .toList();
        return UserRecordEditResponseDTO.builder()
                .userId(userInfo.getId())
                .userRecords(IntStream.range(0, userExercises.size())
                        .mapToObj(i -> UserRecordEditResponseDTO.UserRecord.of(userExercises.get(i), percentages.get(i)))
                        .toList())
                .build();
    }

    public UserProfileEditResponseDTO userProfileEdit(
            final Long userId,
            final MultipartFile profileImage,
            final UserInfo userInfo
    ){
        fileValidator.validateImageFile(profileImage);
        return transactionManager.doUserProfileEditTransaction(profileImage, userInfo);
    }

}
