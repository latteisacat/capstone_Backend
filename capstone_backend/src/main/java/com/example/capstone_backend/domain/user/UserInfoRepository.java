package com.example.capstone_backend.domain.user;

import com.example.capstone_backend.domain.user.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    UserInfo findByEmail(String email);

    @Query("select count(u) from UserInfo u")
    Long userCount();

    @Query("select count(*) from UserInfo u" +
            " where u.bodyScore > :bodyScore"
    )
    Long getBetterBodyScoreUser(Double bodyScore);


    @Query("select u from UserInfo u" +
            " where  :bodyScore*0.85 < u.bodyScore and u.bodyScore < :bodyScore*1.15"
    )
    List<UserInfo> getRecommendedUsers(Double bodyScore);
}
