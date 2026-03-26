package com.example.lms.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonPropertyOrder({"name", "email", "role", "tokenType", "accessToken", "refreshToken", "memberId"})
public class AuthResponse
{
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Long memberId;
    private String email;
    private String name;
    private String role;
}
