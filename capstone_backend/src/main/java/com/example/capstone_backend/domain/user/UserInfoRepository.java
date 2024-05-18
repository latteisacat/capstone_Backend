package com.example.capstone_backend.domain.user;

import com.example.capstone_backend.domain.user.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    UserInfo findByEmail(String email);

    @Query("select count(u) from UserInfo u" + " where u.sex = :sex")
    Long userCount( @Param("sex") String sex);

    @Query("select count(*) from UserInfo u" +
            " where u.bodyScore > :bodyScore and u.sex = :sex"
    )
    Long getBetterBodyScoreUserCount( @Param("bodyScore") Double bodyScore, @Param("sex") String sex);


    //TODO: bodyScore로 검색하는 범위 적절히 수정 필요
    @Query("select u from UserInfo u" +
            " where  :bodyScore*0.7 < u.bodyScore and u.bodyScore < :bodyScore*1.3 and u.sex = :sex"
            + " and u.isDummy = false " + "and u.id != :userId "
    )
    List<UserInfo> getRecommendedUsers( @Param("bodyScore") Double bodyScore, @Param("sex") String sex, @Param("userId") Long userId);
}
