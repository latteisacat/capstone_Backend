package com.example.capstone_backend.domain.community.dto.response;


import lombok.Builder;

import java.util.List;

@Builder
public record CommunityResponseDTO(
    List<CommunityContentDTO> contents,
    Boolean hasNext
) {
    @Builder
    public record CommunityContentDTO(
            String address,
            Long contentId,
            String contentType,
            String thumbnail
    ){}
}
