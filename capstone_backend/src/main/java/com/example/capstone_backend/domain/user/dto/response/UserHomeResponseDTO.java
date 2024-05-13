package com.example.capstone_backend.domain.user.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record UserHomeResponseDTO(
        Long userId,
        String name,
        String profile,
        Double height,
        Double weight,
        Double muscleMass,
        Double bodyFat,
        Double percentageFat,
        String userPercentage,
        List<UserCompetitorDTO> competitors,
        List<UserRecord> userRecords,
        List<AverageRecord> graph,
        List<RecommendedUser> recommendedUsers
) {
    @Builder
    public record UserRecord(
            String sportName,
            String record,
            String percentage
    ){}

    @Builder
    public record RecommendedUser(
            Long userId,
            String name,
            String profile,
            Double height,
            Double weight
    ){}

    @Builder
    public record AverageRecord(
            String name,
            String me,
            String competitor
    ){}
}
