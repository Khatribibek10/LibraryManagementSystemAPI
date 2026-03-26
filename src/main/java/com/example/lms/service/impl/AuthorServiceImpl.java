package com.example.lms.service.impl;

import com.example.lms.dto.request.AuthorRequest;
import com.example.lms.dto.response.AuthorResponse;
import com.example.lms.entity.Author;
import com.example.lms.exception.DuplicateResourceException;
import com.example.lms.exception.ResourceNotFoundException;
import com.example.lms.repository.AuthorRepository;
import com.example.lms.service.AuthorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Service
public class AuthorServiceImpl implements AuthorService {
    private AuthorRepository authorRepository;
    @Override
    public AuthorResponse createAuthor(AuthorRequest request) {
        if(authorRepository.existsByEmail(request.getEmail())){
            throw new DuplicateResourceException("Email Already Used");
        }
        if(authorRepository.existsByMobileNumber(request.getMobileNumber())){
            throw new DuplicateResourceException("Mobile Number Already Used");
        }

        Author author = Author.builder()
                .name(request.getName())
                .email(request.getEmail())
                .mobileNumber(request.getMobileNumber()).
                build();

        author = authorRepository.save(author);

        return mapToResponse(author);
    }

    @Override
    public AuthorResponse getAuthorById(Long id) {
        Author author = findAuthorById(id);
        return mapToResponse(author);
    }

    @Override
    public List<AuthorResponse> getAllAuthors() {
        return authorRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public AuthorResponse updateAuthor(Long id, AuthorRequest request) {
        Author author = findAuthorById(id);

        authorRepository.findByEmail(request.getEmail())
                .filter(a -> !a.getId().equals(id))
                .ifPresent(a -> { throw new DuplicateResourceException("Email already in use: " + request.getEmail()); });

        author.setName(request.getName());
        author.setEmail(request.getEmail());
        author.setMobileNumber(request.getMobileNumber());

        return mapToResponse( authorRepository.save(author));
    }

    @Override
    public void deleteAuthor(Long id) {
        Author author = findAuthorById(id);
        authorRepository.delete(author);
        log.info("Author deleted with id: {}", id);
    }

    // ─── Helpers ────────────────────────────────────────────────────────────────
    private Author findAuthorById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author", "id", id));
    }

    public AuthorResponse mapToResponse(Author author) {
        return AuthorResponse.builder()
                .id(author.getId())
                .name(author.getName())
                .email(author.getEmail())
                .mobileNumber(author.getMobileNumber())
                .build();
    }
}
