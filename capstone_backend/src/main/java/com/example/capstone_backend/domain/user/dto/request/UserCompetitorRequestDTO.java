package com.example.capstone_backend.domain.user.dto.request;


import lombok.Builder;

@Builder
public record UserCompetitorRequestDTO(
        Long userId,
        Long competitorId
) {
}
