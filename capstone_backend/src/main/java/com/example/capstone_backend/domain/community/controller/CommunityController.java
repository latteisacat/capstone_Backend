package com.example.capstone_backend.domain.community.controller;


import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.capstone_backend.common.Response;
import com.example.capstone_backend.common.jwt.CustomUserDetails;
import com.example.capstone_backend.common.util.Tools;
import com.example.capstone_backend.domain.community.dto.request.CommentRequestDTO;
import com.example.capstone_backend.domain.community.dto.request.ContentUploadRequestDTO;
import com.example.capstone_backend.domain.community.dto.response.CommunityResponseDTO;
import com.example.capstone_backend.domain.community.dto.response.ContentResponseDTO;
import com.example.capstone_backend.domain.community.exception.ContentsNotFoundException;
import com.example.capstone_backend.domain.community.exception.ContentsRequiredException;
import com.example.capstone_backend.domain.community.exception.TooManyContentsException;
import com.example.capstone_backend.domain.community.service.CommunityReadService;
import com.example.capstone_backend.domain.community.service.CommunityWriteService;
import com.example.capstone_backend.domain.fileserver.service.FileValidator;
import com.example.capstone_backend.domain.fileserver.service.FileWriteServiceTransactionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TooManyListenersException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/community")
public class CommunityController {

    final private FileWriteServiceTransactionManager transactionManager;
    final private CommunityReadService communityReadService;
    final private CommunityWriteService communityWriteService;
    final private FileValidator fileValidator;


    @GetMapping("")
    public ResponseEntity<?> community(@PageableDefault final Pageable pageable){
        return ResponseEntity.ok(Response.success(communityReadService.getCommunityContents(pageable)));
    }

    @GetMapping("/{contentId}")
    public ResponseEntity<?> communityContent(
            @PathVariable final Long contentId,
            @AuthenticationPrincipal final CustomUserDetails userDetails
    ) {
        if (userDetails == null){
            throw new IllegalArgumentException("user not found or token is null");
        }
        return ResponseEntity.ok(Response.success(communityReadService.getContent(contentId, userDetails)));
    }

    @PostMapping("/{contentId}/comment")
    public ResponseEntity<?> commentContent(
            @PathVariable final Long contentId,
            @RequestBody final CommentRequestDTO commentRequestDTO,
            @AuthenticationPrincipal final CustomUserDetails userDetails
    ){
        Tools.invalidUserCheck(userDetails, commentRequestDTO.userId());

        return ResponseEntity.ok(Response.success(communityWriteService.uploadComment(
                contentId, commentRequestDTO, userDetails.getUserInfo()
        )));
    }

    @PostMapping(value = "/upload", consumes={"multipart/form-data"})
    public ResponseEntity<?> uploadContent(
            @RequestPart("text")final ContentUploadRequestDTO contentUploadRequestDTO,
            @RequestPart(value = "image", required = false)final MultipartFile image,
            @RequestPart(value = "video", required = false)final MultipartFile video,
            @AuthenticationPrincipal final CustomUserDetails userDetails
            ){
        Tools.invalidUserCheck(userDetails, contentUploadRequestDTO.userId());
        contentsValidator(image, video, userDetails, contentUploadRequestDTO);
        return ResponseEntity.ok(Response.success(null));
    }

    private void contentsValidator(
            MultipartFile image,
            MultipartFile video,
            CustomUserDetails userDetails,
            ContentUploadRequestDTO contentUploadRequestDTO) {
        if((image ==null || image.isEmpty())&&(video ==null || video.isEmpty())){
            throw new ContentsRequiredException();
        }
        else if((image != null && !image.isEmpty())&&(video ==null || video.isEmpty()))
        {
            fileValidator.validateImageFile(image);
            transactionManager.doContentUploadTransaction(contentUploadRequestDTO, image, userDetails.getUserInfo(), "image");
        }
        else if((image ==null || image.isEmpty())&&(video !=null && !video.isEmpty()))
        {
            fileValidator.validateVideoFile(video);
            transactionManager.doContentUploadTransaction(contentUploadRequestDTO, video, userDetails.getUserInfo(), "video");
        }
        else{
            throw new TooManyContentsException();
        }
    }
}
