package com.example.capstone_backend.domain.account.dto.request;


import lombok.Builder;

import javax.validation.constraints.NotNull;

@Builder
public record JoinRequestDTO(
        @NotNull
        String password,
        @NotNull
        String name,
        @NotNull
        String email,
        @NotNull
        String sex) {
}
