package com.example.capstone_backend.domain.user.dto.request;


import lombok.Builder;

@Builder
public record UserCompetitorAddDTO(
        Long userId,
        Long competitorId
) {
}
