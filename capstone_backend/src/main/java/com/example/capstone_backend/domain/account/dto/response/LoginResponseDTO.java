package com.example.capstone_backend.domain.account.dto.response;

import lombok.Builder;

@Builder
public record LoginResponseDTO (
        Long userId
){

}
