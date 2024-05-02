package com.example.capstone_backend.domain.user.dto.response;


import lombok.Builder;

@Builder
public record UserProfileEditResponseDTO(
    Long userId,
    String userName,
    String userProfile
) {
}
