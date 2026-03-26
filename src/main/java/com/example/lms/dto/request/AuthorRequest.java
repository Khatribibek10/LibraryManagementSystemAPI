package com.example.lms.dto.request;

import com.example.lms.entity.Book;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

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
