package com.example.capstone_backend.domain.fileserver;

import com.example.capstone_backend.domain.fileserver.entity.Contents;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentsRepository extends JpaRepository<Contents, Long> {

    @Query(" select c " +
            " from Contents c " +
            " where c.userId = :userId "+
            " order by c.createdAt desc ")
    Slice<Contents> findSliceByUserId(Long userId, Pageable pageable);
}
