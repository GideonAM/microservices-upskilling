package com.redeemerlives.products_service.service;

import com.redeemerlives.products_service.dto.CategoryDto;
import com.redeemerlives.products_service.dto.PageResponse;
import com.redeemerlives.products_service.entity.Category;
import com.redeemerlives.products_service.mapper.CategoryMapper;
import com.redeemerlives.products_service.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    public String createCategory(CategoryDto categoryDto) {
        Category category = categoryMapper.toCategory(categoryDto);
        categoryRepository.save(category);
        return "Category created successfully";
    }

    public PageResponse<CategoryDto> getAllCategories(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categories = categoryRepository.findAll(pageable);
        List<CategoryDto> pageCategory =
                categories.stream().map(categoryMapper::toCategoryDto).toList();

        return new PageResponse<>(
                pageCategory,
                categories.getTotalElements(),
                categories.getTotalPages(),
                categories.isLast(),
                categories.isFirst()
        );
    }

    public String deleteCategoryById(String categoryId) {
        categoryRepository.findById(categoryId)
                .ifPresentOrElse(categoryRepository::delete,
                        () -> {throw new EntityNotFoundException("Category not found");}
                );

        return "Category deleted";
    }

    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        Category updatedCategory =  categoryMapper.updatedCategory(categoryDto, category);
        categoryRepository.save(updatedCategory);
        return categoryMapper.toCategoryDto(updatedCategory);
    }

    public List<CategoryDto> searchCategoryByName(String categoryName) {
        List<Category> categories = categoryRepository.findAllByNameContainingIgnoreCase(categoryName);
        return categories.stream().map(categoryMapper::toCategoryDto).toList();
    }
}
