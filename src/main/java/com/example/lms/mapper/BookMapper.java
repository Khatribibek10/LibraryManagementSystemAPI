package com.example.lms.mapper;

import com.example.lms.dto.response.AuthorResponse;
import com.example.lms.dto.response.BookResponse;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;

@Mapper
public interface BookMapper {

    @Select("""
SELECT 
    b.id AS book_id,
    b.name AS book_name,
    b.no_of_pages,
    b.isbn,
    b.rating,
    b.stock_count,
    b.published_date,
    b.photo,
    b.category_id
FROM tbl_book b
""")
    @Results({
            @Result(column = "book_id", property = "id"),
            @Result(column = "book_name", property = "name"),
            @Result(column = "no_of_pages", property = "noOfPages"),
            @Result(column = "isbn", property = "isbn"),
            @Result(column = "rating", property = "rating"),
            @Result(column = "stock_count", property = "stockCount"),
            @Result(column = "published_date", property = "publishedDate"),
            @Result(column = "photo", property = "photo"),
            @Result(column = "category_id", property = "category",
                    one = @One(select = "com.example.lms.mapper.CategoryMapper.getCategoryById")),
            @Result(column = "book_id", property = "authors",
                    many = @Many(select = "getAuthorsByBookId"))
    })
    List<BookResponse> getAllBooks();

    @Select("""
        SELECT a.id, a.name, a.email, a.mobile_number
        FROM tbl_author a
        JOIN tbl_book_author ba ON a.id = ba.author_id
        WHERE ba.book_id = #{bookId}
    """)
    Set<AuthorResponse> getAuthorsByBookId(Long bookId);



    @Select("""
        SELECT 
            b.id AS book_id,
            b.name AS book_name,
            b.no_of_pages,
            b.isbn,
            b.rating,
            b.stock_count,
            b.published_date,
            b.photo,
            b.category_id
        FROM tbl_book b
        WHERE b.id = #{bookId}
    """)
    @Results({
            @Result(column = "book_id", property = "id"),
            @Result(column = "book_name", property = "name"),
            @Result(column = "no_of_pages", property = "noOfPages"),
            @Result(column = "isbn", property = "isbn"),
            @Result(column = "rating", property = "rating"),
            @Result(column = "stock_count", property = "stockCount"),
            @Result(column = "published_date", property = "publishedDate"),
            @Result(column = "photo", property = "photo"),
            @Result(column = "category_id", property = "category",
                    one = @One(select = "com.example.lms.mapper.CategoryMapper.getCategoryById")),
            @Result(column = "book_id", property = "authors",
                    many = @Many(select = "getAuthorsByBookId"))
    })
    BookResponse getBookById(Long bookId);
}