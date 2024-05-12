package com.example.capstone_backend.domain.account.service;


import com.example.capstone_backend.domain.account.dto.request.JoinRequestDTO;
import com.example.capstone_backend.domain.account.dto.request.LoginRequestDTO;
import com.example.capstone_backend.domain.user.UserInfoRepository;
import com.example.capstone_backend.domain.user.entity.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final UserInfoRepository userInfoRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void join(JoinRequestDTO joinRequestDTO) {
        userInfoRepository.save(UserInfo.builder()
                .email(joinRequestDTO.email())
                .userName(joinRequestDTO.name())
                .userPassword(bCryptPasswordEncoder.encode(joinRequestDTO.password()))
                .createdAt(LocalDate.now())
                .updatedAt(LocalDate.now())
                .build());
    }
    public boolean checkEmail(String email){
        boolean result = false;
        if(userInfoRepository.findByEmail(email) == null){
            result = true;
        }
        return result;
    }

}
