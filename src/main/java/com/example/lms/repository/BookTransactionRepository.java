package com.example.lms.repository;

import com.example.lms.entity.BookTransaction;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.*;

public interface BookTransactionRepository extends JpaRepository<BookTransaction, Long> {
    Optional<BookTransaction> findByCode(String code);

    List<BookTransaction> findByMemberId(Long memberId);

    List<BookTransaction> findByBookId(Long bookId);

    // Find active (not returned) transaction for a member + book
    @Query("SELECT t FROM BookTransaction t WHERE t.member.id = :memberId AND t.book.id = :bookId AND t.activeClosed = true")
    Optional<BookTransaction> findActiveTransactionByMemberAndBook(
            @Param("memberId") Long memberId,
            @Param("bookId") Long bookId);

    // All active rentals
    List<BookTransaction> findByActiveClosedTrue();

    // Count active rentals for a specific book
    @Query("SELECT COUNT(t) FROM BookTransaction t WHERE t.book.id = :bookId AND t.activeClosed = true")
    long countActiveRentalsForBook(@Param("bookId") Long bookId);
}
