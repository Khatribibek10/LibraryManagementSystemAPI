package com.example.lms.dto.response;

import lombok.Builder;
import lombok.Data;

import java.awt.*;

@Data
@Builder
public class CategoryResponse {
    private Long id;
    private String name;
    private TextArea description;
}
