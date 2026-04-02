package com.example.lms.mapper;

import com.example.lms.dto.response.BookTransactionResponse;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper

public interface BookTransactionMapper {

    @Select("""
    SELECT 
        t.id,
        t.code,
        t.from_date,
        t.to_date,
        t.rent_status,
        t.active_closed,
        t.book_id,
        b.name AS book_name,
        t.member_id,
        m.name AS member_name
    FROM tbl_book_transaction t
    JOIN tbl_book b ON t.book_id = b.id
    JOIN tbl_member m ON t.member_id = m.id
""")
    @Results({
            @Result(column="id", property="id"),
            @Result(column="code", property="code"),
            @Result(column="from_date", property="fromDate"),
            @Result(column="to_date", property="toDate"),
            @Result(column="rent_status", property="rentStatus"),
            @Result(column="active_closed", property="activeClosed"),
            @Result(column="book_id", property="bookId"),
            @Result(column="book_name", property="bookName"),
            @Result(column="member_id", property="memberId"),
            @Result(column="member_name", property="memberName")
    })
    List<BookTransactionResponse> getAllBookTransaction();

    @Select("""
        SELECT 
            t.id,
            t.code,
            t.from_date,
            t.to_date,
            t.rent_status,
            t.active_closed,
            t.book_id,
            b.name AS book_name,
            t.member_id,
            m.name AS member_name
        FROM tbl_book_transaction t
        JOIN tbl_book b ON t.book_id = b.id
        JOIN tbl_member m ON t.member_id = m.id 
        WHERE t.member_id = #{memberId}
    """)
    @Results({
            @Result(column="id", property="id"),
            @Result(column="code", property="code"),
            @Result(column="from_date", property="fromDate"),
            @Result(column="to_date", property="toDate"),
            @Result(column="rent_status", property="rentStatus"),
            @Result(column="active_closed", property="activeClosed"),
            @Result(column="book_id", property="bookId"),
            @Result(column="book_name", property="bookName"),
            @Result(column="member_id", property="memberId"),
            @Result(column="member_name", property="memberName")
    })
    List<BookTransactionResponse> getAllTransactionsByMemeber(@Param("memberId") Long memberId);


    @Select("""
    SELECT 
        t.id,
        t.code,
        t.from_date,
        t.to_date,
        t.rent_status,
        t.active_closed,
        t.book_id,
        b.name AS book_name,
        t.member_id,
        m.name AS member_name
    FROM tbl_book_transaction t
    JOIN tbl_book b ON t.book_id = b.id
    JOIN tbl_member m ON t.member_id = m.id
    where t.active_closed = #{status}
""")
    @Results({
            @Result(column="id", property="id"),
            @Result(column="code", property="code"),
            @Result(column="from_date", property="fromDate"),
            @Result(column="to_date", property="toDate"),
            @Result(column="rent_status", property="rentStatus"),
            @Result(column="active_closed", property="activeClosed"),
            @Result(column="book_id", property="bookId"),
            @Result(column="book_name", property="bookName"),
            @Result(column="member_id", property="memberId"),
            @Result(column="member_name", property="memberName")
    })
    List<BookTransactionResponse> getTransactionByStatus(@Param("status") Boolean status);
}