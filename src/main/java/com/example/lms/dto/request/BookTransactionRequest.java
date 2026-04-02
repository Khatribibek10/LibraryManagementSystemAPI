package com.example.lms.dto.request;

import com.example.lms.enums.RentType;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookTransactionRequest {
    @NotNull(message = "Book ID is required")
    private Long bookId;

    @NotNull(message = "Member ID is required")
    private Long memberId;

    @NotNull(message = "Rent type is required")
    private RentType rentType;

    @Future(message = "To date must be a future date")
    private LocalDate toDate;
}
