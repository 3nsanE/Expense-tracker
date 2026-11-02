package com.newlearn.expenses_api.mapper;

import com.newlearn.expenses_api.dto.request.CategoryRequest;
import com.newlearn.expenses_api.dto.response.CategoryResponse;
import com.newlearn.expenses_api.entity.Category;
import com.newlearn.expenses_api.entity.User;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public Category toEntity(CategoryRequest request, User user) {
        return Category.builder().
                name(request.getName()).
                description(request.getDescription()).
                user(user).
                build();
    }

    public CategoryResponse toResponse(Category category) {
        return CategoryResponse.builder().
                id(category.getId()).
                name(category.getName()).
                description(category.getDescription()).
                build();
    }

    public void updateEntity(CategoryRequest request, Category category) {
        category.setName(request.getName());
        category.setDescription(request.getDescription());
    }
}
