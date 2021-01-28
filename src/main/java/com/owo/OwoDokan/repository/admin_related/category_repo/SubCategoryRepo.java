package com.owo.OwoDokan.repository.admin_related.category_repo;

import com.owo.OwoDokan.entity.admin_related.category.SubCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubCategoryRepo extends JpaRepository<SubCategoryEntity, Integer> {
}
