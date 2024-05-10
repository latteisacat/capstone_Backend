package com.example.capstone_backend.domain.community.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ContentResponseDTO(
        String profileImage,
        Long userId,
        String text,
        String content,
        List<UserComment> comments,
        Boolean hasNext

) {
    @Builder
    public record UserComment(
            Long commentId,
            Long userId,
            String profileImage,
            String comment
    ) {
    }
}
