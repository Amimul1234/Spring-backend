package com.shopKpr.repository.adminRelated.category_repo;

import com.shopKpr.entity.admin_related.category.SubCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubCategoryRepo extends JpaRepository<SubCategoryEntity, Long> {
}
