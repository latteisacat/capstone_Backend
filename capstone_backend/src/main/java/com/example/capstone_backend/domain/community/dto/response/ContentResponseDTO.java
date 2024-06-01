package com.example.capstone_backend.domain.community.dto.response;

import com.example.capstone_backend.domain.fileserver.entity.Comments;
import com.example.capstone_backend.domain.fileserver.entity.Contents;
import com.example.capstone_backend.domain.user.entity.UserInfo;
import lombok.Builder;

import java.util.List;

@Builder
public record ContentResponseDTO(
        String profileImage,
        Long userId,
        String text,
        String content,
        List<UserComment> comments,
        String thumbnail,
        Long connectedUserId,
        String connectedUserProfile

) {
    @Builder
    public record UserComment(
            Long commentId,
            Long userId,
            String profileImage,
            String comment
    ) {
        public static UserComment of(final Comments comments) {
            return UserComment.builder()
                    .commentId(comments.getId())
                    .userId(comments.getUserId().getId())
                    .profileImage(comments.getUserId().getUserProfile())
                    .comment(comments.getComments())
                    .build();
        }
    }
    public static ContentResponseDTO of(final Contents contents, final List<Comments> comments, final UserInfo connectedUser){
        return ContentResponseDTO.builder()
                .profileImage(contents.getUserId().getUserProfile())
                .userId(contents.getUserId().getId())
                .text(contents.getContentText())
                .content(contents.getContents())
                .thumbnail(contents.getThumbnail())
                .comments(comments.stream().map(UserComment::of).toList())
                .connectedUserId(connectedUser.getId())
                .connectedUserProfile(connectedUser.getUserProfile())
                .build();
    }
}
