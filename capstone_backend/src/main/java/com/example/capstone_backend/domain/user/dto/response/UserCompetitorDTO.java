package com.example.capstone_backend.domain.user.dto.response;


import lombok.Builder;

import java.util.List;

@Builder
public record UserCompetitorDTO(
        Long userId,
        String userProfile,
        String userName,
        List<List<CompareDetail>> userCompare
) {
    @Builder
    public record CompareDetail(
            String name,
            Double me,
            Double competitor
    ){
        public static List<CompareDetail> of(String name, Double me, Double competitor){
            return List.of(CompareDetail.builder()
                    .name(name)
                    .me(me)
                    .competitor(competitor)
                    .build());
        }
    }
}
