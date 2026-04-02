package com.example.lms.service;

import com.example.lms.dto.request.CategoryRequest;
import com.example.lms.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAllCategory();
    CategoryResponse create(CategoryRequest categoryRequest);
    CategoryResponse getById(Long id);
    CategoryResponse update(Long id, CategoryRequest categoryRequest);
    void delete(Long id);
}
