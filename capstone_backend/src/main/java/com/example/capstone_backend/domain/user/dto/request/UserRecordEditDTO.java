package com.example.capstone_backend.domain.user.dto.request;

import lombok.Builder;

@Builder
public record UserRecordEditDTO(
        Long userId,
        String exerciseName,
        Integer record
) {
}
