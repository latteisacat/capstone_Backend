package com.example.capstone_backend.domain.user.dto.response;

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
    ){}
}
