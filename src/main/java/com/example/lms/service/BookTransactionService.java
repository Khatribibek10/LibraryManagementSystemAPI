package com.example.lms.service;

import com.example.lms.dto.request.BookTransactionRequest;
import com.example.lms.dto.response.BookTransactionResponse;

import java.util.List;

public interface BookTransactionService {
    BookTransactionResponse rentBook(BookTransactionRequest request);
    BookTransactionResponse returnBook(BookTransactionRequest request);
    BookTransactionResponse getTransactionById(Long id);
    BookTransactionResponse getTransactionByCode(String code);
    List<BookTransactionResponse> getAllTransactions();
    List<BookTransactionResponse> getTransactionsByMember(Long memberId);
    List<BookTransactionResponse> getTransactionsByBook(Long bookId);
    List<BookTransactionResponse> getTransactionByStatus(Boolean status);
}
