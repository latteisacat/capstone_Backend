package com.example.capstone_backend.domain.user.dto.response;

import com.example.capstone_backend.domain.user.entity.Exercise;
import lombok.Builder;

import java.util.List;

@Builder
public record UserRecordEditResponseDTO(
        Long userId,
        List<UserRecord> userRecords
) {
    @Builder
    public record UserRecord(
            String sportName,
            String record,
            String percentage,
            String contents
    ){
        public static UserRecord of(Exercise exercise, Double percentage){
            //TODO: kg 외에 달리기 같은 운동을 위해 km/h같은 단위 추가 할 것
            return UserRecord.builder()
                    .sportName(exercise.getExerciseName())
                    .record(exercise.getRecord() + "kg")
                    .percentage(percentage + "%")
                    .contents(exercise.getContents())
                    .build();
        }
    }
}
