package com.example.capstone_backend.domain.fileserver;


import com.example.capstone_backend.domain.fileserver.entity.Comments;
import com.example.capstone_backend.domain.fileserver.entity.Contents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {


    @Query(" select c " +
            " from Comments c " +
            " join fetch c.contentId ct "+
            " join fetch c.userId u "+
            " where c.contentId = :contents ")
    List<Comments> findAllByContents(Contents contents);
}
