package com.example.lms.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.awt.*;

@Data
public class CategoryRequest {
    @NotBlank(message = "Category Name is Required.")
    private String name;
    
    private TextArea description;
}
