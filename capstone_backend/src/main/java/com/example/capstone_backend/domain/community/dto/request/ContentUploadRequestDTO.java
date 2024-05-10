package com.example.capstone_backend.domain.community.dto.request;


import lombok.Builder;

@Builder
public record ContentUploadRequestDTO(
        Long userId,
        String text
) {
}
