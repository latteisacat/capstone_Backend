package com.example.capstone_backend.domain.user.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record UserInfoResponseDTO(
        Long userId,
        String name,
        String profile,
        Double height,
        Double weight,
        Double muscleMass,
        Double bodyFat,
        Double BMI,
        Double percentageFat,
        String userPercentage,
        List<UserRecord> userRecords
) {
    @Builder
    public record UserRecord(
            String sportName,
            String record,
            String percentage,
            String contents
    ){}

}
