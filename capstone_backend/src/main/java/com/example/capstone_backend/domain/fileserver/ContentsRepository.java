package com.example.capstone_backend.domain.fileserver;

import com.example.capstone_backend.domain.fileserver.entity.Contents;
import com.example.capstone_backend.domain.user.entity.UserInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentsRepository extends JpaRepository<Contents, Long> {

    @Query(" select c " +
            " from Contents c " +
            " join fetch c.userId u " +
            " order by c.createdAt desc ")
    Slice<Contents> findSliceBy(Pageable pageable);

    @Query(" select c " +
            " from Contents c " +
            " where c.userId = :userId "+
            " order by c.createdAt desc ")
    List<Contents> findAllByUserId(@Param("userId") UserInfo userId);


    @Query(" select c " +
            " from Contents c " +
            " where c.datatype = 'video' "+
            " order by rand() ")
    List<Contents> findAllVideos();
}
