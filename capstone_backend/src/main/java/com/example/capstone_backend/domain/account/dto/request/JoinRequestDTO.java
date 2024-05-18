package com.example.capstone_backend.domain.account.dto.request;


import lombok.Builder;

@Builder
public record JoinRequestDTO(
        String password,
        String name,
        String email,
        String sex) {
}
