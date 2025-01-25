package com.redeemerlives.products_service.controller;

import com.redeemerlives.products_service.dto.CategoryDto;
import com.redeemerlives.products_service.dto.PageResponse;
import com.redeemerlives.products_service.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<String> createCategory(@RequestBody CategoryDto categoryDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(categoryDto));
    }

    @GetMapping
    public ResponseEntity<PageResponse<CategoryDto>> getAllCategories(
            @RequestParam(required = false, name = "page", value = "0") int page,
            @RequestParam(required = false, name = "size", value = "10") int size
    ) {
        return ResponseEntity.ok(categoryService.getAllCategories(page, size));
    }

    @GetMapping("{category-name}")
    public ResponseEntity<List<CategoryDto>> searchCategoryByName(@PathVariable("category-name") String categoryName) {
        return ResponseEntity.ok(categoryService.searchCategoryByName(categoryName));
    }

    @PutMapping("/{category-id}")
    public ResponseEntity<CategoryDto> updateCategory(
            @RequestBody CategoryDto categoryDto,
            @PathVariable("category-id") String categoryId)
    {
        return ResponseEntity.ok(categoryService.updateCategory(categoryDto, categoryId));
    }

    @DeleteMapping("/{category-id}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable("category-id") String categoryId) {
        return ResponseEntity.ok(categoryService.deleteCategoryById(categoryId));
    }

}
