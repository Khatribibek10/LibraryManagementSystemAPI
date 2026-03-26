package com.example.lms.service;

import com.example.lms.dto.request.AuthorRequest;
import com.example.lms.dto.response.AuthorResponse;

import java.util.List;

public interface AuthorService {
    AuthorResponse createAuthor(AuthorRequest request);
    AuthorResponse getAuthorById(Long id);
    List<AuthorResponse> getAllAuthors();
    AuthorResponse updateAuthor(Long id, AuthorRequest request);
    void deleteAuthor(Long id);
}
