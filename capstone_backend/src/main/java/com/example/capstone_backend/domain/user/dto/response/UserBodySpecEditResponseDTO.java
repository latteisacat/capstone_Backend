package com.example.capstone_backend.domain.user.dto.response;


import lombok.Builder;

@Builder
public record UserBodySpecEditResponseDTO(
        Long userId,
        Double height,
        Double weight,
        Double muscleMass,
        Double bodyFat,
        Double BMI,
        Double percentageFat
) {
}
