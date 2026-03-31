package com.example.lms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {
    private Long id;
    private String name;
    private Integer noOfPages;
    private String isbn;
    private Double rating;
    private Integer stockCount;
    private LocalDate publishedDate;
    private String photo;
    private CategoryResponse category;
    private Set<AuthorResponse> authors;

}
