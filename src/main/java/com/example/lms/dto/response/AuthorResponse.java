package com.example.lms.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonPropertyOrder({"id", "name", "email", "mobileNumber"})
public class AuthorResponse {
    private Long id;
    private String name;
    private String email;
    private String mobileNumber;
}
