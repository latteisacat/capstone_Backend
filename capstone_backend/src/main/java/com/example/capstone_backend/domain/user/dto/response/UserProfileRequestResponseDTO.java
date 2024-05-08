package com.example.capstone_backend.domain.user.dto.response;


import lombok.Builder;

import java.util.List;

@Builder
public record UserProfileRequestResponseDTO(
        String profileImage,
        Long userId,
        List<userContents> userContents,
        Boolean hasNext

) {
    @Builder
    public record userContents(
            String address,
            Long contentId,
            String contentType,
            String thumbnail
    ) {

    }
}
