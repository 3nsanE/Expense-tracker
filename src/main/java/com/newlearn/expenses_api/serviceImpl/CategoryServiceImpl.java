package com.newlearn.expenses_api.serviceImpl;

import com.newlearn.expenses_api.dto.request.CategoryRequest;
import com.newlearn.expenses_api.dto.response.CategoryResponse;
import com.newlearn.expenses_api.entity.Category;
import com.newlearn.expenses_api.entity.User;
import com.newlearn.expenses_api.exception.DuplicateResourceException;
import com.newlearn.expenses_api.exception.ResourceNotFoundException;
import com.newlearn.expenses_api.mapper.CategoryMapper;
import com.newlearn.expenses_api.repository.CategoryRepository;
import com.newlearn.expenses_api.repository.UserRepository;
import com.newlearn.expenses_api.service.CategoryService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final CategoryMapper categoryMapper;

    // Create Category
    @Override
    public CategoryResponse createCategory(Long userId, CategoryRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with ID: " + userId
                ));

        if (categoryRepository.existsByNameAndUserId(request.getName(), userId)) {
            throw new DuplicateResourceException(
                    "Category already exists: " + request.getName()
            );
        }

        Category category = categoryMapper.toEntity(request, user);

        Category savedCategory = categoryRepository.save(category);

        return categoryMapper.toResponse(savedCategory);
    }

    // Get All Categories
    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException(
                    "User not found with ID: " + userId
            );
        }

        return categoryRepository.findByUserId(userId)
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    // Get Category By ID
    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(Long categoryId, Long userId) {

        Category category = categoryRepository.findByIdAndUserId(categoryId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category not found with ID: " + categoryId
                ));

        return categoryMapper.toResponse(category);
    }

    // Update Category
    @Override
    public CategoryResponse updateCategory(
            Long categoryId, Long userId, CategoryRequest request) {

        Category category = categoryRepository.findByIdAndUserId(categoryId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category not found with ID: " + categoryId
                ));

        if (!category.getName().equalsIgnoreCase(request.getName())
                && categoryRepository.existsByNameAndUserId(request.getName(), userId)) {

            throw new DuplicateResourceException(
                    "Category already exists: " + request.getName()
            );
        }

        categoryMapper.updateEntity(request, category);

        Category updatedCategory = categoryRepository.save(category);

        return categoryMapper.toResponse(updatedCategory);
    }

    // Delete Category
    @Override
    public void deleteCategory(Long categoryId, Long userId) {

        Category category = categoryRepository.findByIdAndUserId(categoryId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category not found with ID: " + categoryId
                ));

        categoryRepository.delete(category);
    }
}