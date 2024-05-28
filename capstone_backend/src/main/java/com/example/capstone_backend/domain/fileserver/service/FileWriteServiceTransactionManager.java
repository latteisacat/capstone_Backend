package com.example.capstone_backend.domain.fileserver.service;


import com.example.capstone_backend.common.util.Tools;
import com.example.capstone_backend.domain.community.dto.request.ContentUploadRequestDTO;
import com.example.capstone_backend.domain.fileserver.ContentsRepository;
import com.example.capstone_backend.domain.fileserver.S3FileRepository;
import com.example.capstone_backend.domain.fileserver.entity.Contents;
import com.example.capstone_backend.domain.user.ExerciseRepository;
import com.example.capstone_backend.domain.user.UserInfoRepository;
import com.example.capstone_backend.domain.user.dto.request.UserRecordEditDTO;
import com.example.capstone_backend.domain.user.dto.response.UserProfileEditResponseDTO;
import com.example.capstone_backend.domain.user.dto.response.UserRecordEditResponseDTO;
import com.example.capstone_backend.domain.user.entity.Exercise;
import com.example.capstone_backend.domain.user.entity.UserInfo;
import com.example.capstone_backend.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Transactional
public class FileWriteServiceTransactionManager {

    private final S3FileRepository fileRepository;
    private final ContentsRepository contentsRepository;
    private final ExerciseRepository exerciseRepository;
    private final UserInfoRepository userInfoRepository;

    public void doContentUploadTransaction(
            final ContentUploadRequestDTO contentUploadRequestDTO,
            final MultipartFile file,
            final UserInfo userInfo,
            final String contentsType
    ){
        final String fileUrl;
        final List<String> savedFileUrls = new ArrayList<>();
        try{
            fileUrl = fileRepository.save(file);
            savedFileUrls.add(fileUrl);
            contentsRepository.save(
                    Contents.builder()
                            .userId(userInfo)
                            .contents(fileUrl)
                            .contentText(contentUploadRequestDTO.text())
                            .thumbnail(Tools.thumbnailUrl(fileUrl))
                            .datatype(contentsType)
                            .thumbnail(null)
                            .build()
            );
        }
        catch (final RuntimeException e) {
            fileRepository.deleteAll(savedFileUrls);
            throw e;
        }
    }

    public List<Exercise> doExerciseRecordUploadTransaction(
            final UserRecordEditDTO userRecordEditDTO,
            final MultipartFile file,
            final UserInfo userInfo
    ){
        final String fileUrl;
        final List<String> savedFileUrls = new ArrayList<>();
        final Double record = Tools.parsingRecord(userRecordEditDTO.record());
        List<Exercise> userExercises;
        try{
            userExercises = exerciseRepository.findAllByUser(userInfo);
            OptionalInt indexOpt = IntStream.range(0, userExercises.size())
                    .filter(i -> userExercises.get(i).getExerciseName().equals(userRecordEditDTO.exerciseName()))
                    .findFirst();

            int index = indexOpt.orElse(-1);
            if(index == -1){
                fileUrl = fileRepository.save(file);
                savedFileUrls.add(fileUrl);
                Exercise saveExercise = Exercise.builder()
                        .userId(userInfo)
                        .exerciseName(userRecordEditDTO.exerciseName())
                        .record(record)
                        .contents(fileUrl)
                        .build();
                userExercises.add(saveExercise);
                exerciseRepository.save(saveExercise);
            }
            else{
                fileRepository.overwrite(file, Tools.getFileNameFromUrl(userExercises.get(index).getContents()));
                userExercises.get(index).setRecord(record);
            }
        }
        catch (final RuntimeException e) {
            fileRepository.deleteAll(savedFileUrls);
            throw e;
        }
        return userExercises;
    }

    public UserProfileEditResponseDTO doUserProfileEditTransaction(
            final MultipartFile profileImage,
            final UserInfo userInfo
    ){
        final String fileUrl;
        final String previousFileUrl = userInfo.getUserProfile();
        final List<String> savedFileUrls = new ArrayList<>();
        try{
            if(previousFileUrl == null){
                fileUrl = fileRepository.save(profileImage);
                savedFileUrls.add(fileUrl);
                userInfo.setUserProfile(fileUrl);
                userInfoRepository.save(userInfo);
            }
            else{
                fileRepository.overwrite(profileImage, Tools.getFileNameFromUrl(previousFileUrl));
                fileUrl = previousFileUrl;
            }
        }
        catch (final RuntimeException e) {
            fileRepository.deleteAll(savedFileUrls);
            throw e;
        }
        return UserProfileEditResponseDTO.builder()
                .userId(userInfo.getId())
                .userName(userInfo.getUserName())
                .userProfile(fileUrl)
                .build();
    }

}
