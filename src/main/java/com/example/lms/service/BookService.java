package com.example.lms.service;

import com.example.lms.dto.request.BookRequest;
import com.example.lms.dto.response.BookResponse;

import java.util.List;

public interface BookService {
    BookResponse getBookById(Long id);
    List<BookResponse> getAllBooks();
    BookResponse createBook(BookRequest request);
    BookResponse updateBook(Long id, BookRequest request);
    void deleteBook(Long id);
}
