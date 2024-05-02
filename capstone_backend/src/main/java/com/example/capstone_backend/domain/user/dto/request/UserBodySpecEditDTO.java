package com.example.capstone_backend.domain.user.dto.request;

import lombok.Builder;

@Builder
public record UserBodySpecEditDTO(
        Double height,
        Double weight,
        Double muscleMass,
        Double fatMass
) {
}
