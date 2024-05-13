package com.example.capstone_backend.domain.community.service;

import com.example.capstone_backend.domain.community.dto.response.CommunityResponseDTO;
import com.example.capstone_backend.domain.community.dto.response.ContentResponseDTO;
import com.example.capstone_backend.domain.fileserver.CommentsRepository;
import com.example.capstone_backend.domain.fileserver.ContentsRepository;
import com.example.capstone_backend.domain.fileserver.entity.Comments;
import com.example.capstone_backend.domain.fileserver.entity.Contents;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommunityReadService {
    private final CommentsRepository commentsRepository;
    private final ContentsRepository contentsRepository;

    public CommunityResponseDTO getCommunityContents(final Pageable pageable){
        Slice<Contents> contents = contentsRepository.findSliceBy(pageable);
        return CommunityResponseDTO.of(contents, contents.hasNext());
    }

    public ContentResponseDTO getContent(final Long contentId){
        Contents content = contentsRepository.findById(contentId).orElseThrow();
        List<Comments> comments = commentsRepository.findAllByContents(content);
        return ContentResponseDTO.of(content, comments);
    }


}
