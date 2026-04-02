package com.example.lms.service.impl;

import com.example.lms.dto.request.BookRequest;
import com.example.lms.dto.response.AuthorResponse;
import com.example.lms.dto.response.BookResponse;
import com.example.lms.dto.response.CategoryResponse;
import com.example.lms.entity.Author;
import com.example.lms.entity.Book;
import com.example.lms.entity.Category;
import com.example.lms.exception.DuplicateResourceException;
import com.example.lms.exception.ResourceNotFoundException;
import com.example.lms.mapper.BookMapper;
import com.example.lms.repository.AuthorRepository;
import com.example.lms.repository.BookRepository;
import com.example.lms.repository.CategoryRepository;
import com.example.lms.service.BookService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;


    @Override
    public BookResponse getBookById(Long id) {
        log.info("GetBOOKID(): {}", id);
       return bookMapper.getBookById(id);
    }

    @Override
    public List<BookResponse> getAllBooks() {
        return bookMapper.getAllBooks();
    }

    @Override
    public BookResponse createBook(BookRequest request) {

        if(request.getIsbn() != null && bookRepository.existsByIsbn(request.getIsbn()))
        {
            throw new DuplicateResourceException("Book with isbn already exists: " +request.getIsbn());
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", request.getCategoryId()));

        Set<Author> authors = new HashSet<>(authorRepository.findAllById(request.getAuthorIds()));

        if (authors.size() != request.getAuthorIds().size()) {
            throw new ResourceNotFoundException("Author", "ids", request.getAuthorIds());
        }

        Book book = Book.builder()
                .name(request.getName())
                .noOfPages(request.getNoOfPages())
                .isbn(request.getIsbn())
                .rating(request.getRating())
                .stockCount(request.getStockCount())
                .publishedDate(request.getPublishedDate())
                .photo(request.getPhoto())
                .category(category)
                .authors(authors)
                .build();

        bookRepository.save(book);

        return mapToResponse(book);
    }

    @Override
    public BookResponse updateBook(Long id, BookRequest request) {
        Book book = findBookById(id);

        if (request.getIsbn() != null) {
            boolean isbnExists = bookRepository.existsByIsbnAndIdNot(request.getIsbn(), id);
            if (isbnExists) {
                throw new DuplicateResourceException(
                        "Book with ISBN already exists: " + request.getIsbn()
                );
            }
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", request.getCategoryId()));

        Set<Author> authors = new HashSet<>(authorRepository.findAllById(request.getAuthorIds()));

        if (authors.size() != request.getAuthorIds().size()) {
            throw new ResourceNotFoundException("Author", "ids", request.getAuthorIds());
        }

        book.setName(request.getName());
        book.setNoOfPages(request.getNoOfPages());
        book.setIsbn(request.getIsbn());
        book.setRating(request.getRating());
        book.setStockCount(request.getStockCount());
        book.setPublishedDate(request.getPublishedDate());
        book.setPhoto(request.getPhoto());
        book.setCategory(category);
        book.setAuthors(authors);

        bookRepository.save(book);

        return mapToResponse(book);


    }

    @Override
    public void deleteBook(Long id) {
        Book book = findBookById(id);
        bookRepository.delete(book);
        log.info("Book deleted with id: {}", id);
    }


    // ─── Helpers ────────────────────────────────────────────────────────────────
    private Book findBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
    }

    public BookResponse mapToResponse(Book book) {

        Set<AuthorResponse> authorResponses = book.getAuthors().stream().map(
                author -> AuthorResponse.builder()
                        .id(author.getId())
                        .name(author.getName())
                        .email(author.getEmail())
                        .mobileNumber(author.getMobileNumber())
                        .build()
        ).collect(Collectors.toSet());

        CategoryResponse categoryResponse = CategoryResponse.builder()
                .id(book.getCategory().getId())
                .name(book.getCategory().getName())
                .description(book.getCategory().getDescription())
                .build();


        return BookResponse.builder()
                .id(book.getId())
                .name(book.getName())
                .noOfPages(book.getNoOfPages())
                .isbn(book.getIsbn())
                .rating(book.getRating())
                .stockCount(book.getStockCount())
                .publishedDate(book.getPublishedDate())
                .photo(book.getPhoto())
                .authors(authorResponses)
                .category(categoryResponse)
                .build();
    }
}
