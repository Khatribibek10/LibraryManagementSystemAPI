package com.example.lms.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class BookRequest {
    @NotBlank(message = "Book name is required")
    private String name;

    @Positive(message = "No. of pages must be positive")
    private Integer noOfPages;

    private String isbn;

    @DecimalMin(value = "0.0") @DecimalMax(value = "5.0")
    private Double rating;

    @NotNull(message = "Stock count is required")
    @Min(value = 0, message = "Stock count cannot be negative")
    private Integer stockCount;

    private LocalDate publishedDate;

    private String photo;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotEmpty(message = "At least one author is required")
    private Set<Long> authorIds;
}
