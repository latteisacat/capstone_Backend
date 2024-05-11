package com.example.capstone_backend.domain.user.dto.response;


import com.example.capstone_backend.domain.fileserver.entity.Contents;
import lombok.Builder;
import org.springframework.data.domain.Slice;

import java.util.List;

@Builder
public record UserProfileRequestResponseDTO(
        String profileImage,
        Long userId,
        List<UserContents> userContents,
        Boolean hasNext

) {
    @Builder
    public record UserContents(
            String address,
            Long contentId,
            String contentType,
            String thumbnail
    ) {
        public static UserContents of(Contents contents) {
            return UserContents.builder()
                    .address(contents.getContents())
                    .contentId(contents.getId())
                    .contentType(contents.getDatatype())
                    .thumbnail(contents.getThumbnail())
                    .build();
        }

    }
    public static UserProfileRequestResponseDTO of(
            final String profileImage,
            final Long userId,
            final Slice<Contents> userContents
    ) {
        return new UserProfileRequestResponseDTO(
                profileImage,
                userId,
                userContents.getContent().stream().map(UserContents::of).toList(),
                userContents.hasNext());
    }

}
