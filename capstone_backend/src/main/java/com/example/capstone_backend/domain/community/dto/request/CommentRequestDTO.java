package com.example.capstone_backend.domain.community.dto.request;


import lombok.Builder;

@Builder
public record CommentRequestDTO(
        Long userId,
        Long contentId,
        String text
) {

}
