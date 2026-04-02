package com.example.lms.service.impl;

import com.example.lms.dto.request.CategoryRequest;
import com.example.lms.dto.response.CategoryResponse;
import com.example.lms.entity.Category;
import com.example.lms.exception.DuplicateResourceException;
import com.example.lms.exception.ResourceNotFoundException;
import com.example.lms.mapper.CategoryMapper;
import com.example.lms.repository.CategoryRepository;
import com.example.lms.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
private final CategoryMapper categoryMapper;
private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryResponse> getAllCategory() {
        return categoryMapper.getAll();
    }

//    @Override
//    public CategoryResponse create(CategoryRequest categoryRequest) {
////        categoryMapper.insert(categoryRequest);
////        return new CategoryResponse();
//
//        Category category = new Category();
//        category.setName(categoryRequest.getName());
//        category.setDescription(categoryRequest.getDescription());
//
//        categoryMapper.insert(category);
//
//        return CategoryResponse.builder()
//                .id(category.getId())
//                .name(category.getName())
//                .description(category.getDescription())
//                .build();
//
//    }

    @Override
    public CategoryResponse create(CategoryRequest categoryRequest){
        if(categoryRepository.existsByName(categoryRequest.getName())){
            throw new DuplicateResourceException("Name Already Used");
        }

        Category category = Category.builder()
                            .name(categoryRequest.getName())
                            .description(categoryRequest.getDescription())
                            .build();

        categoryRepository.save(category);
        return mapToResponse(category);
    }

    @Override
    public CategoryResponse getById(Long id) {
        Category category = findCategoryEntityById(id);
        return mapToResponse(category);
    }

    @Override
    public CategoryResponse update(Long id, CategoryRequest categoryRequest) {
        Category category = findCategoryEntityById(id);
        categoryRepository.findByName(categoryRequest.getName())
                .filter(a -> !a.getId().equals(id))
                .ifPresent(a -> { throw new DuplicateResourceException("Name already in use: " + categoryRequest.getName()); });

        category.setName(category.getName());
        category.setDescription(categoryRequest.getDescription());
        categoryRepository.save(category);

        return mapToResponse(category);
    }

    @Override
    public void delete(Long id) {
        Category category = findCategoryEntityById(id);
        categoryRepository.delete(category);
        return;
    }


    // ─── Helpers ────────────────────────────────────────────────────────────────
    private Category findCategoryEntityById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
    }

    public CategoryResponse mapToResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }


}
