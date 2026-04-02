package com.example.lms.controller;

import com.example.lms.dto.request.AuthorRequest;
import com.example.lms.dto.response.ApiResponse;
import com.example.lms.dto.response.AuthorResponse;
import com.example.lms.service.AuthorService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/authors")
@RestController
@AllArgsConstructor
@Slf4j
@SecurityRequirement(name = "bearerAuth")
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<AuthorResponse>>> getAll(){
        return ResponseEntity.ok(ApiResponse.success("All Authors Fetched", authorService.getAllAuthors()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AuthorResponse>> getById(@PathVariable Long id){
        return ResponseEntity.ok(ApiResponse.success("Author Fetched", authorService.getAuthorById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteById(@PathVariable Long id){
        authorService.deleteAuthor(id);
        return ResponseEntity.ok(ApiResponse.success("Data Deleted Successfully"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AuthorResponse>> create(@RequestBody AuthorRequest request){
        AuthorResponse response = authorService.createAuthor(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Author Created.", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AuthorResponse>> update(@PathVariable Long id, @RequestBody AuthorRequest request){
        AuthorResponse response = authorService.updateAuthor(id, request);
        return ResponseEntity.ok(ApiResponse.success("Author Updated Successfully!!!", response));
    }


}
