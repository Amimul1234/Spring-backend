package com.owo.OwoDokan.service.admin_related.category;

import com.owo.OwoDokan.entity.admin_related.category.CategoryEntity;
import com.owo.OwoDokan.entity.admin_related.category.SubCategoryEntity;
import com.owo.OwoDokan.repository.adminRelated.category_repo.CategoryRepo;
import com.owo.OwoDokan.repository.adminRelated.category_repo.SubCategoryRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SubCategoryService {
    private final static Logger logger = LoggerFactory.getLogger(SubCategoryService.class);
    private final SubCategoryRepo subCategoryRepo;
    private final CategoryRepo categoryRepo;

    public SubCategoryService(SubCategoryRepo subCategoryRepo, CategoryRepo categoryRepo) {
        this.subCategoryRepo = subCategoryRepo;
        this.categoryRepo = categoryRepo;
    }

    public ResponseEntity addNewSubCategory(Long categoryId, SubCategoryEntity subCategoryEntity) {

        Optional<CategoryEntity> categoryEntity = categoryRepo.findById(categoryId);

        if(categoryEntity.isPresent())
        {
            CategoryEntity categoryEntity1 = categoryEntity.get();

            subCategoryEntity.setCategoryEntity(categoryEntity1);
            categoryEntity1.getSubCategoryEntities().add(subCategoryEntity);

            try
            {
                categoryRepo.save(categoryEntity1);
                return ResponseEntity.status(HttpStatus.OK).body("Sub category added successfully");
            }
            catch (Exception e)
            {
                logger.error("Sub category service failed, Error is: "+e.getMessage());
                return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("Failed to add new sub category");
            }

        }
        else
        {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("Can not find the requested category");
        }
    }

    public ResponseEntity updateSubCategory(SubCategoryEntity subCategoryEntity) {
        Optional<SubCategoryEntity> subCategoryEntity1 = subCategoryRepo.findById(subCategoryEntity.getSub_category_id());

        if(subCategoryEntity1.isPresent())
        {
            SubCategoryEntity subCategoryEntity2 = subCategoryEntity1.get();

            subCategoryEntity2.setCategoryEntity(subCategoryEntity.getCategoryEntity());
            subCategoryEntity2.setSub_category_name(subCategoryEntity.getSub_category_name());
            subCategoryEntity.setSub_category_image(subCategoryEntity.getSub_category_image());

            try
            {
                subCategoryRepo.save(subCategoryEntity2);
                return ResponseEntity.status(HttpStatus.OK).body("Sub category info updated successfully");
            }
            catch (Exception e)
            {
                logger.error("Sub category service error, Error is: "+e.getMessage());
                return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("Error updating sub category info, please try again");
            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("Sub category does not exists");
        }
    }

    public ResponseEntity deleteSubCategory(Long subCategoryId) {
        Optional<SubCategoryEntity> subCategoryEntity = subCategoryRepo.findById(subCategoryId);

        if(subCategoryEntity.isPresent())
        {
            try
            {
                subCategoryRepo.delete(subCategoryEntity.get());
                return ResponseEntity.status(HttpStatus.OK).body("Sub category deleted successfully");
            }
            catch (Exception e)
            {
                logger.error("Sub category service error, Error is: "+e.getMessage());
                return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("Error deleting sub category, please try again");
            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("Sub category does not exists");
        }
    }

    public ResponseEntity getAllSubCategories(Long categoryId) {

        Optional<CategoryEntity> categoryEntity = categoryRepo.findById(categoryId);

        if(categoryEntity.isPresent())
        {
            CategoryEntity categoryEntity1 = categoryEntity.get();

            List<SubCategoryEntity> subCategoryEntityList = categoryEntity1.getSubCategoryEntities();
            return ResponseEntity.status(HttpStatus.OK).body(subCategoryEntityList);
        }

        else
        {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
