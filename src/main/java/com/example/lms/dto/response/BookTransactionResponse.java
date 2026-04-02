package com.example.lms.dto.response;

import com.example.lms.enums.RentType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class BookTransactionResponse {
    private Long id;
    private String code;
    private LocalDate fromDate;
    private LocalDate toDate;
    private RentType rentStatus;
    private Boolean activeClosed;
    private Long bookId;
    private String bookName;
    private Long memberId;
    private String memberName;
}
