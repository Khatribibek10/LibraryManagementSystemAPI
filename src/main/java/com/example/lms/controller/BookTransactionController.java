package com.example.lms.controller;

import com.example.lms.dto.request.BookTransactionRequest;
import com.example.lms.dto.response.ApiResponse;
import com.example.lms.dto.response.BookTransactionResponse;
import com.example.lms.service.BookTransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class BookTransactionController {

    private final BookTransactionService transactionService;


    @PostMapping("/rent")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ResponseEntity<ApiResponse<BookTransactionResponse>> rent(
            @Valid @RequestBody BookTransactionRequest request) {
        BookTransactionResponse response = transactionService.rentBook(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Book rented successfully", response));
    }

    @PostMapping("/return")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ResponseEntity<ApiResponse<BookTransactionResponse>> returnBook(
            @Valid @RequestBody BookTransactionRequest request) {
        BookTransactionResponse response = transactionService.returnBook(request);
        return ResponseEntity.ok(ApiResponse.success("Book returned successfully", response));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ResponseEntity<ApiResponse<BookTransactionResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Transaction fetched", transactionService.getTransactionById(id)));
    }

    @GetMapping("/code/{code}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ResponseEntity<ApiResponse<BookTransactionResponse>> getByCode(@PathVariable String code) {
        return ResponseEntity.ok(ApiResponse.success("Transaction fetched", transactionService.getTransactionByCode(code)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ResponseEntity<ApiResponse<List<BookTransactionResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("All transactions", transactionService.getAllTransactions()));
    }

    @GetMapping("/filterByStatus")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ResponseEntity<ApiResponse<List<BookTransactionResponse>>> getTransactionByStatus(@RequestParam(required = false, defaultValue = "true") Boolean status) {
        return ResponseEntity.ok(ApiResponse.success("Active transactions", transactionService.getTransactionByStatus(status)));
    }

    @GetMapping("/member/{memberId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN') or #memberId == authentication.principal.id")
    public ResponseEntity<ApiResponse<List<BookTransactionResponse>>> getByMember(@PathVariable Long memberId) {
        return ResponseEntity.ok(ApiResponse.success("Member transactions", transactionService.getTransactionsByMember(memberId)));
    }

    @GetMapping("/book/{bookId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ResponseEntity<ApiResponse<List<BookTransactionResponse>>> getByBook(@PathVariable Long bookId) {
        return ResponseEntity.ok(ApiResponse.success("Book transactions", transactionService.getTransactionsByBook(bookId)));
    }
}
