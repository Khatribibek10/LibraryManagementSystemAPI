package com.example.lms.dto.response.solidiare;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class PaginationResponse<T> {
    private List<T> content;
    private int totalElements;
    private int totalPages;
    private int page;
    private int size;
}
