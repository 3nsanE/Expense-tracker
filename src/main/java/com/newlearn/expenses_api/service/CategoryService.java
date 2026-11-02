package com.newlearn.expenses_api.service;

import com.newlearn.expenses_api.dto.request.CategoryRequest;
import com.newlearn.expenses_api.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(Long userId, CategoryRequest request);
    List<CategoryResponse> getAllCategories(Long userId);
    CategoryResponse getCategoryById(Long categoryId, Long userId);
    CategoryResponse updateCategory(Long categoryId,Long userId, CategoryRequest request);

    void deleteCategory(Long categoryId, Long userId);
}
