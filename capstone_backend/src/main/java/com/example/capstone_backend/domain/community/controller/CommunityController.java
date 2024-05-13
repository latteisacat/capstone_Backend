package com.example.capstone_backend.domain.community.controller;


import com.example.capstone_backend.common.Response;
import com.example.capstone_backend.domain.community.dto.request.ContentUploadRequestDTO;
import com.example.capstone_backend.domain.community.dto.response.CommunityResponseDTO;
import com.example.capstone_backend.domain.community.dto.response.ContentResponseDTO;
import com.example.capstone_backend.domain.community.service.CommunityReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/community")
public class CommunityController {

    final private CommunityReadService communityReadService;

    @GetMapping("")
    public ResponseEntity<?> community(@PageableDefault final Pageable pageable){
        communityReadService.getCommunityContents(pageable);
        return ResponseEntity.ok(Response.success(dummyCommunityResponseDTO()));
    }

    private static CommunityResponseDTO dummyCommunityResponseDTO(){
        List<CommunityResponseDTO.CommunityContentDTO> communityContents = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            communityContents.add(CommunityResponseDTO.CommunityContentDTO.builder()
                    .contentId((long)i)
                    .address("content.asdf.asdf")
                    .contentType("image")
                    .thumbnail("content.asdf.asdf")
                    .build());
        }
        return CommunityResponseDTO.builder()
                .contents(communityContents)
                .hasNext(false)
                .build();
    }
    @GetMapping("/{contentId}")
    public ResponseEntity<?> communityContent(
            @PathVariable final Long contentId) {
        communityReadService.getContent(contentId);
        return ResponseEntity.ok(Response.success(dummyCommunityContentResponseDTO()));
    }

    private static ContentResponseDTO dummyCommunityContentResponseDTO(){
        List<ContentResponseDTO.UserComment> comments = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            comments.add(ContentResponseDTO.UserComment.builder()
                    .commentId((long)i + 2)
                    .userId((long)i)
                    .profileImage("profile.asdf.asdf")
                    .comment("comment")
                    .build());
        }
        return ContentResponseDTO.builder()
                .profileImage("profile.asdf.asdf")
                .userId(1L)
                .text("text")
                .content("content")
                .thumbnail("content.asdf.asdf")
                .comments(comments)
                .build();
    }

    @PostMapping(value = "/upload", consumes={"multipart/form-data"})
    public ResponseEntity<?> uploadContent(
            @RequestPart("text")final ContentUploadRequestDTO contentUploadRequestDTO,
            @RequestPart(value = "image", required = false)final MultipartFile image,
            @RequestPart(value = "video", required = false)final MultipartFile video
            ){
        return ResponseEntity.ok(Response.success(null));
    }
}
