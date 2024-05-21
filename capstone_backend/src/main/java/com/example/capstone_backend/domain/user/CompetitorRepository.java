package com.example.capstone_backend.domain.user;

import com.example.capstone_backend.domain.user.entity.Competitor;
import com.example.capstone_backend.domain.user.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompetitorRepository extends JpaRepository<Competitor, Long> {


    @Query("select c from Competitor c "
            + " join fetch c.toUserId"
            + " join fetch c.fromUserId"
            + " where c.fromUserId = :user")
    List<Competitor> findAllByFromUserId(@Param("user") UserInfo user);

}
