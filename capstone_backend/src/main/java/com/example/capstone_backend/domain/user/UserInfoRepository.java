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

    @Query("select u from UserInfo u" +
            " where  :bodyScore*(1.0 - :range) < u.bodyScore and u.bodyScore < :bodyScore*(1.0 + :range) and u.sex = :sex"
            +" and u.height > :height - 7.0 and u.height < :height + 7.0 "
            + " and u.isDummy = false " + "and u.id != :userId "
    )
    List<UserInfo> getRecommendedUsers(
            @Param("bodyScore") Double bodyScore,
            @Param("sex") String sex,
            @Param("userId") Long userId,
            @Param("height") Double height,
            @Param("range")Double range);
}
