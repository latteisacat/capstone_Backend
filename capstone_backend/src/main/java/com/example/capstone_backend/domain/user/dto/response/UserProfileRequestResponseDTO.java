package com.example.capstone_backend.domain.user.dto.response;


import com.example.capstone_backend.domain.fileserver.entity.Contents;
import com.example.capstone_backend.domain.user.entity.Exercise;
import lombok.Builder;
import org.springframework.data.domain.Slice;

import java.util.List;

@Builder
public record UserProfileRequestResponseDTO(
        String profileImage,
        Long userId,
        String userName,
        List<UserContents> userContents,
        List<UserExerciseRecord> userExerciseRecords

) {
    @Builder
    public record UserContents(
            String address,
            Long contentId,
            String contentType,
            String thumbnail
    ) {
        public static UserContents of(Contents contents) {
            return UserContents.builder()
                    .address(contents.getContents())
                    .contentId(contents.getId())
                    .contentType(contents.getDatatype())
                    .thumbnail(contents.getThumbnail())
                    .build();
        }

    }

    @Builder
    public record UserExerciseRecord(
            String exerciseName,
            String record,
            String video
    ) {
        // TODO : 나중에 kg외에 다른 것도 파싱해야함.
        public static UserExerciseRecord of(Exercise exercise) {
            return UserExerciseRecord.builder()
                    .exerciseName(exercise.getExerciseName())
                    .record(exercise.getRecord() + "kg")
                    .video(exercise.getContents())
                    .build();
        }
    }
    public static UserProfileRequestResponseDTO of(
            final String profileImage,
            final Long userId,
            final String userName,
            final List<Contents> userContents,
            final List<Exercise> userExercises
    ) {
        return new UserProfileRequestResponseDTO(
                profileImage,
                userId,
                userName,
                userContents.stream().map(UserContents::of).toList(),
                userExercises.stream().map(UserExerciseRecord::of).toList()
        );
    }

}
