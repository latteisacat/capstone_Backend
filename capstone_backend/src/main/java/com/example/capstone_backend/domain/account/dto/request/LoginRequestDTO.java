package com.example.capstone_backend.domain.account.dto.request;


import lombok.Builder;

@Builder
public record LoginRequestDTO(
        String username,
        String password
) {
}
