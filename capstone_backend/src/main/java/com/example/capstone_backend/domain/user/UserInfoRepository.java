package com.example.capstone_backend.domain.user;

import com.example.capstone_backend.domain.user.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    UserInfo findByEmail(String email);

    @Query("select count(u) from UserInfo u" + " where u.sex = :sex")
    Long userCount(String sex);

    @Query("select count(*) from UserInfo u" +
            " where u.bodyScore > :bodyScore and u.sex = :sex"
    )
    Long getBetterBodyScoreUser(Double bodyScore, String sex);


    @Query("select u from UserInfo u" +
            " where  :bodyScore*0.85 < u.bodyScore and u.bodyScore < :bodyScore*1.15 and u.sex = :sex"
    )
    List<UserInfo> getRecommendedUsers(Double bodyScore, String sex);
}
