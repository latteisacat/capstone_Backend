package com.example.capstone_backend.domain.user.service;

import com.example.capstone_backend.domain.fileserver.ContentsRepository;
import com.example.capstone_backend.domain.fileserver.entity.Contents;
import com.example.capstone_backend.domain.user.UserInfoRepository;
import com.example.capstone_backend.domain.user.dto.response.UserProfileRequestResponseDTO;
import com.example.capstone_backend.domain.user.entity.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserReadService {

    private final UserInfoRepository userInfoRepository;
    private final ContentsRepository contentsRepository;

    public UserProfileRequestResponseDTO getUserProfileRequest(final Long userId){
        UserInfo user = userInfoRepository.findById(userId).orElseThrow();
        List<Contents> userContents = contentsRepository.findAllByUserId(user);
        return UserProfileRequestResponseDTO.of(
                user.getUserProfile(),
                user.getId(),
                userContents
        );
    }
}
