package com.redeemerlives.products_service.mapper;

import com.redeemerlives.products_service.dto.CategoryDto;
import com.redeemerlives.products_service.entity.Category;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toCategory(CategoryDto categoryDto) {
        return Category.builder()
                .name(categoryDto.name())
                .description(categoryDto.description())
                .build();
    }

    public CategoryDto toCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    public Category updatedCategory(CategoryDto categoryDto, Category category) {
        if (StringUtils.isNotBlank(categoryDto.name()))
            category.setName(categoryDto.name());

        if (StringUtils.isNotBlank(categoryDto.description()))
            category.setDescription(categoryDto.description());

        return category;
    }
}
