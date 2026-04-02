package com.example.lms.controller;

import com.example.lms.dto.request.CategoryRequest;
import com.example.lms.dto.response.ApiResponse;
import com.example.lms.dto.response.CategoryResponse;
import com.example.lms.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/category")
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAll(){
        return ResponseEntity.ok(ApiResponse.success("Category Fetched Successfully!!!", categoryService.getAllCategory()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> create(@RequestBody CategoryRequest request){
        return ResponseEntity.ok(ApiResponse.success("Category Created Successfully", categoryService.create(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getById(@PathVariable Long id){
        return ResponseEntity.ok(ApiResponse.success("Category Details Fetched", categoryService.getById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> update(@PathVariable Long id, @RequestBody CategoryRequest request){
        return ResponseEntity.ok(ApiResponse.success("Category Update Successfully", categoryService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id){
        categoryService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Data Deleted Successfully"));
    }

}
