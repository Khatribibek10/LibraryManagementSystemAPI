package com.example.lms.service.impl;

import com.example.lms.dto.request.BookTransactionRequest;
import com.example.lms.dto.response.BookTransactionResponse;
import com.example.lms.entity.Book;
import com.example.lms.entity.BookTransaction;
import com.example.lms.entity.Member;
import com.example.lms.enums.RentType;
import com.example.lms.exception.*;
import com.example.lms.exception.BadRequestException;
import com.example.lms.exception.InsufficientStockException;
import com.example.lms.exception.ResourceNotFoundException;
import com.example.lms.mapper.BookTransactionMapper;
import com.example.lms.repository.BookRepository;
import com.example.lms.repository.BookTransactionRepository;
import com.example.lms.repository.MemberRepository;
import com.example.lms.service.BookTransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class BookTransactionServiceImpl implements BookTransactionService {
    private final BookTransactionRepository transactionRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final BookTransactionMapper transactionMapper;

    @Override
    public BookTransactionResponse rentBook(BookTransactionRequest request) {
        Book book = findBook(request.getBookId());
        Member member = findMember(request.getMemberId());

        // Stock check
        if (book.getStockCount() <= 0) {
            throw new InsufficientStockException("Book '" + book.getName() + "' is currently out of stock.");
        }

        // Already Rented Check
        transactionRepository.findActiveTransactionByMemberAndBook(member.getId(), book.getId())
                .ifPresent(t -> { throw new BadRequestException("Member already has an active rental for this book."); });

        // Stock Decrease
        book.setStockCount(book.getStockCount() - 1);
        bookRepository.save(book);

        BookTransaction transaction = BookTransaction.builder()
                .code(generateTransactionCode())
                .fromDate(LocalDate.now())
                .toDate(request.getToDate())
                .rentStatus(RentType.RENT)
                .activeClosed(true)
                .book(book)
                .member(member)
                .build();

        transaction = transactionRepository.save(transaction);
        log.info("Book rented: book={}, member={}, code={}", book.getId(), member.getId(), transaction.getCode());
        return mapToResponse(transaction);
    }

    @Override
    public BookTransactionResponse returnBook(BookTransactionRequest request) {
        BookTransaction transaction = transactionRepository
                .findActiveTransactionByMemberAndBook(request.getMemberId(), request.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No active rental found for memberId=" + request.getMemberId()
                                + " and bookId=" + request.getBookId()));

        Book book = transaction.getBook();

        // Increase stock back
        book.setStockCount(book.getStockCount() + 1);
        bookRepository.save(book);

        // Close the transaction
        transaction.setRentStatus(RentType.RETURN);
        transaction.setActiveClosed(false);
        transaction.setToDate(LocalDate.now());

        transaction = transactionRepository.save(transaction);
        log.info("Book returned: book={}, member={}, code={}", book.getId(), request.getMemberId(), transaction.getCode());
        return mapToResponse(transaction);
    }

    @Override
    public BookTransactionResponse getTransactionById(Long id) {
        return mapToResponse(findTransactionById(id));
    }

    @Override
    public BookTransactionResponse getTransactionByCode(String code) {
        BookTransaction transaction = transactionRepository.findByCode(code).orElseThrow(()-> new ResourceNotFoundException("Rent Details not found with "));
        return mapToResponse(transaction);
    }

    @Override
    public List<BookTransactionResponse> getAllTransactions() {
        return transactionMapper.getAllBookTransaction();
    }

    @Override
    public List<BookTransactionResponse> getTransactionsByMember(Long memberId) {
        return transactionMapper.getAllTransactionsByMemeber(memberId);
    }

    @Override
    public List<BookTransactionResponse> getTransactionsByBook(Long bookId) {
        return List.of();
    }

    @Override
    public List<BookTransactionResponse> getTransactionByStatus(Boolean status) {
        return transactionMapper.getTransactionByStatus(status);
    }


    //=================================Helpers===================================
    private Book findBook(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", bookId));
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member", "id", memberId));
    }

    private BookTransaction findTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BookTransaction", "id", id));
    }

    private String generateTransactionCode() {
        return "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public BookTransactionResponse mapToResponse(BookTransaction t) {
        return BookTransactionResponse.builder()
                .id(t.getId())
                .code(t.getCode())
                .fromDate(t.getFromDate())
                .toDate(t.getToDate())
                .rentStatus(t.getRentStatus())
                .activeClosed(t.getActiveClosed())
                .bookId(t.getBook().getId())
                .bookName(t.getBook().getName())
                .memberId(t.getMember().getId())
                .memberName(t.getMember().getName())
                .build();
    }
}
