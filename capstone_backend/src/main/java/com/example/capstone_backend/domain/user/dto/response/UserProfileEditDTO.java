package com.example.capstone_backend.domain.user.dto.response;


import lombok.Builder;

@Builder
public record UserProfileEditDTO(
    Long userId,
    String userName,
    String userProfile
) {
}
