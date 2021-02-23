package com.owo.OwoDokan.service.admin_related.category;

import com.owo.OwoDokan.entity.admin_related.category.CategoryEntity;
import com.owo.OwoDokan.repository.adminRelated.category_repo.CategoryRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final static Logger logger = LoggerFactory.getLogger(CategoryService.class);
    private final CategoryRepo categoryRepo;

    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Transactional
    public String addNewCategory(CategoryEntity categoryEntity) {
        try
        {
            categoryRepo.save(categoryEntity);
            return "Category created successfully";
        }catch (Exception e)
        {
            logger.error("Error saving category entity, Error is: " + e.getMessage());
            return "Can not create category, please try again";
        }
    }

    public ResponseEntity updateCategory(Long categoryId, CategoryEntity categoryEntity) {

        Optional<CategoryEntity> categoryEntity1 = categoryRepo.findById(categoryId);

        if(categoryEntity1.isPresent())
        {
            CategoryEntity categoryEntity2 = categoryEntity1.get();

            categoryEntity2.setCategoryId(categoryId);
            categoryEntity2.setCategoryImage(categoryEntity.getCategoryImage());
            categoryEntity2.setCategoryName(categoryEntity.getCategoryName());
            categoryEntity2.setSubCategoryEntities(categoryEntity2.getSubCategoryEntities());

            try
            {
                categoryRepo.save(categoryEntity2);
                return ResponseEntity.status(HttpStatus.OK).body("Category details updated successfully");
            }catch (Exception e)
            {
                logger.error("Error updating category details, Error is: "+e.getMessage());
                return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("Error updating category details");
            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("Can not find requested category");
        }
    }

    public ResponseEntity deleteCategory(Long categoryId) {

        Optional<CategoryEntity> categoryEntity = categoryRepo.findById(categoryId);

        if(categoryEntity.isPresent())
        {
            try
            {
                categoryRepo.delete(categoryEntity.get());
                return ResponseEntity.status(HttpStatus.OK).body("Category deleted successfully");
            }catch (Exception e)
            {
                logger.error("Can not delete category, Error: "+e.getMessage());
                return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("Can not delete category, please try again");
            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can not find category");
        }
    }

    public List<CategoryEntity> getAllCategories() {
        return categoryRepo.findAll();
    }
}
