package com.owo.OwoDokan.repository.admin_related.category_repo;

import com.owo.OwoDokan.entity.admin_related.category.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<CategoryEntity, String> {
}
