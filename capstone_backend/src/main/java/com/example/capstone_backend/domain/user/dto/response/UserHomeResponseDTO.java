package com.example.capstone_backend.domain.user.dto.response;

import com.example.capstone_backend.domain.fileserver.entity.Contents;
import com.example.capstone_backend.domain.user.entity.Exercise;
import com.example.capstone_backend.domain.user.entity.UserInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
public record UserHomeResponseDTO(
        Long userId,
        String name,
        String sex,
        String profile,
        Double height,
        Double weight,
        Double muscleMass,
        Double bodyFat,
        Double percentageFat,
        String userPercentage,
        List<UserCompetitorDTO> competitors,
        List<UserRecord> userRecords,
        List<AverageRecord> graph,

        List<ShortForm> contents,

        List<NullCompetitor> nullGraph,
        List<RecommendedUser> recommendedUsers
) {
    @Builder
    public record UserRecord(
            String sportName,
            String record,
            String percentage
    ){
    }

    @Builder
    public record RecommendedUser(
            Long userId,
            String name,
            String profile,
            Double height,
            Double weight
    ){
        public static RecommendedUser of(UserInfo userInfo){
            return RecommendedUser.builder()
                    .userId(userInfo.getId())
                    .name(userInfo.getUserName())
                    .profile(userInfo.getUserProfile())
                    .height(userInfo.getHeight())
                    .weight(userInfo.getWeight())
                    .build();
        }
    }

    @Builder
    public record AverageRecord(
            String name,
            String me,
            String average
    ){}

    @Builder
    public record NullCompetitor(
            String name,
            Double me,
            Double competitor
    ){}

    @Builder
    public record ShortForm(
            String address,
            Long contentId,
            Long userId,
            String userName,
            String userProfile,
            String contentType,
            String thumbnail
    ){
        public static ShortForm of(final Contents contents){
            return ShortForm.builder()
                    .address(contents.getContents())
                    .contentId(contents.getId())
                    .userId(contents.getUserId().getId())
                    .userName(contents.getUserId().getUserName())
                    .userProfile(contents.getUserId().getUserProfile())
                    .contentType(contents.getDatatype())
                    .thumbnail(contents.getThumbnail())
                    .build();
        }
    }
}
