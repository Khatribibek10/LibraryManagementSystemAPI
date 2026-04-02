package com.example.lms.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthorRequest {
    @NotBlank(message = "Name is Required.")
    private String name;

    @NotBlank(message = "Email is required.")
    @Email(message= "Invalid Email format")
    private String email;

    @NotBlank(message = "Mobile Number is required.")
    private String mobileNumber;
}
