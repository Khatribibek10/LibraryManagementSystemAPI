package com.example.lms.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OAuthResponse {

    private boolean success;
    private String message;
    private DataResponse data;

    @Data
    public static class DataResponse {
        @JsonProperty("access_token")
        private String accessToken;

        @JsonProperty("expires_in")
        private Long expiresIn;

        @JsonProperty("refresh_token")
        private String refreshToken;

        // You can add other fields as needed
        @JsonProperty("token_type")
        private String tokenType;
    }
}