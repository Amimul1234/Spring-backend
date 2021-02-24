package com.owo.OwoDokan.service.admin_related.category;

import com.owo.OwoDokan.entity.admin_related.category.CategoryEntity;
import com.owo.OwoDokan.entity.admin_related.category.SubCategoryEntity;
import com.owo.OwoDokan.exceptions.CategoryNotFoundException;
import com.owo.OwoDokan.repository.adminRelated.category_repo.CategoryRepo;
import com.owo.OwoDokan.repository.adminRelated.category_repo.SubCategoryRepo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
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

    @Transactional
    public String updateSubCategory(Long categoryId, SubCategoryEntity subCategoryEntity) {

        Optional<CategoryEntity> categoryEntityOptional = categoryRepo.findById(categoryId);

        if(categoryEntityOptional.isPresent())
        {
            CategoryEntity categoryEntity = categoryEntityOptional.get();

            List<SubCategoryEntity> subCategoryEntityList = categoryEntity.getSubCategoryEntities();

            for(SubCategoryEntity subCategoryEntity1 : subCategoryEntityList)
            {
                if(subCategoryEntity.getSub_category_id().equals(subCategoryEntity1.getSub_category_id()))
                {
                    subCategoryEntity1.setSub_category_name(subCategoryEntity.getSub_category_name());
                    subCategoryEntity1.setSub_category_image(subCategoryEntity.getSub_category_image());
                    break;
                }
            }

            categoryEntity.setSubCategoryEntities(subCategoryEntityList);

            try
            {
                categoryRepo.save(categoryEntity);
                return "Sub category info updated successfully";
            }
            catch (Exception e)
            {
                logger.error("Sub category service error, Error is: "+e.getMessage());
                throw new RuntimeException(e);
            }
        }
        else
        {
            throw new CategoryNotFoundException(categoryId);
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

    public List<SubCategoryEntity> getAllSubCategories(Long categoryId) {

        Optional<CategoryEntity> categoryEntity = categoryRepo.findById(categoryId);

        if(categoryEntity.isPresent())
        {
            CategoryEntity categoryEntity1 = categoryEntity.get();
            return categoryEntity1.getSubCategoryEntities();
        }
        else
        {
            log.error("Can not find category");
            throw new CategoryNotFoundException(categoryId);
        }
    }
}
