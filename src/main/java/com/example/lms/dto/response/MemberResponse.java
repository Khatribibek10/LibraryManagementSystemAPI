package com.example.lms.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberResponse {
    private Long id;
    private String email;
    private String name;
    private String mobileNo;
    private String address;
    private String role;
}
