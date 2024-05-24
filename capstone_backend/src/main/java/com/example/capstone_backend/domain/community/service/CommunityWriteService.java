package com.example.capstone_backend.domain.community.service;


import com.example.capstone_backend.domain.community.dto.request.CommentRequestDTO;
import com.example.capstone_backend.domain.community.dto.response.ContentResponseDTO;
import com.example.capstone_backend.domain.fileserver.CommentsRepository;
import com.example.capstone_backend.domain.fileserver.ContentsRepository;
import com.example.capstone_backend.domain.fileserver.entity.Comments;
import com.example.capstone_backend.domain.fileserver.entity.Contents;
import com.example.capstone_backend.domain.user.entity.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommunityWriteService {

    private final CommentsRepository commentsRepository;

    private final ContentsRepository contentsRepository;
    public ContentResponseDTO uploadComment(
            final Long contentId,
            final CommentRequestDTO commentRequestDTO,
            final UserInfo user
    ){
        Contents content = contentsRepository.findById(contentId).orElseThrow();
        Comments comment = Comments.builder()
                .contentId(content)
                .userId(user)
                .comments(commentRequestDTO.text())
                .build();
        List<Comments> comments = commentsRepository.findAllByContents(content);
        comments.add(comment);
        commentsRepository.save(comment);
        return ContentResponseDTO.of(content, comments);
    }
}
