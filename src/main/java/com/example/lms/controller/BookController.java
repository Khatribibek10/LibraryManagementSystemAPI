package com.example.lms.controller;

import com.example.lms.dto.request.BookRequest;
import com.example.lms.dto.response.ApiResponse;
import com.example.lms.dto.response.BookResponse;
import com.example.lms.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class BookController {
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<BookResponse>>> getAll(){
        return ResponseEntity.ok(ApiResponse.success("All Books Fetched", bookService.getAllBooks()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponse>> getBookById(@PathVariable Long id){
        return ResponseEntity.ok(ApiResponse.success("Book Details", bookService.getBookById(id)));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    @Operation(
            summary = "Create Books",
            description = "Requires ADMIN or LIBRARIAN role"
    )
    public ResponseEntity<ApiResponse<BookResponse>> create(
            @Valid @RequestBody BookRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Book created successfully", bookService.createBook(request)));
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    @Operation(
            summary = "Update Book",
            description = "Requires ADMIN or LIBRARIAN role"
    )
    public ResponseEntity<ApiResponse<BookResponse>> update(
            @PathVariable Long id, @Valid @RequestBody BookRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Book updated", bookService.updateBook(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN', 'LIBRARIAN')")
    @Operation(
            summary = "Delete Book",
            description = "Requires ADMIN or LIBRARIAN role"
    )
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok(ApiResponse.success("Book deleted successfully"));
    }


}
