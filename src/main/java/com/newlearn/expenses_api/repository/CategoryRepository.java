package com.newlearn.expenses_api.repository;

import com.newlearn.expenses_api.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findByUserId(Long userId);

    Optional<Category> findByIdAndUserId(Long id,Long userId);

    boolean existsByNameAndUserId(String name,Long userId);
    boolean existsByIdAndUserId(Long id,Long userId);
}
