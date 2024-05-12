package com.example.capstone_backend.domain.community.dto.response;


import com.example.capstone_backend.domain.fileserver.entity.Contents;
import lombok.Builder;
import org.springframework.data.domain.Slice;

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
            Long userId,
            String contentType,
            String thumbnail
    ){
        public static CommunityContentDTO of(final Contents contents){
            return CommunityContentDTO.builder()
                    .address(contents.getContents())
                    .contentId(contents.getId())
                    .userId(contents.getUserId().getId())
                    .contentType(contents.getDatatype())
                    .thumbnail(contents.getThumbnail())
                    .build();
        }
    }
    public static CommunityResponseDTO of(final Slice<Contents> contents, final Boolean hasNext){
        return CommunityResponseDTO.builder()
                .contents(contents.stream().map(CommunityContentDTO::of).toList())
                .hasNext(hasNext)
                .build();
    }
}
