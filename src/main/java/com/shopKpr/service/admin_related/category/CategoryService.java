package com.shopKpr.service.admin_related.category;

import com.shopKpr.entity.admin_related.category.CategoryEntity;
import com.shopKpr.exceptions.CategoryNotFoundException;
import com.shopKpr.repository.adminRelated.category_repo.CategoryRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
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

    @Transactional
    public String updateCategory(Long categoryId, CategoryEntity categoryEntity) {

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
                return "Category details updated successfully";
            }catch (Exception e)
            {
                logger.error("Error updating category details, Error is: "+e.getMessage());
                throw new RuntimeException(e);
            }
        }
        else
        {
            throw new CategoryNotFoundException(categoryId);
        }
    }

    @Transactional
    public String deleteCategory(Long categoryId) {

        Optional<CategoryEntity> categoryEntity = categoryRepo.findById(categoryId);

        if(categoryEntity.isPresent())
        {
            try
            {
                categoryRepo.delete(categoryEntity.get());
                return "Category deleted successfully";
            }catch (Exception e)
            {
                logger.error("Can not delete category, Error: "+e.getMessage());
                throw new RuntimeException(e);
            }
        }
        else
        {
            throw new CategoryNotFoundException(categoryId);
        }
    }

    public List<CategoryEntity> getAllCategories() {
        return categoryRepo.findAll();
    }

    public CategoryEntity getCategoryByID(Long categoryId) {

        Optional<CategoryEntity> categoryEntityOptional = categoryRepo.findById(categoryId);

        if(categoryEntityOptional.isPresent())
        {
            return categoryEntityOptional.get();
        }
        else
        {
            throw new CategoryNotFoundException(categoryId);
        }
    }

    public List<String> getCategoriesByIds(List<Long> categoryIds) {

        List<CategoryEntity> categoryEntityList = categoryRepo.findAll();

        List<String> categoryNames = new ArrayList<>();

        for(CategoryEntity categoryEntity : categoryEntityList)
        {
            for(Long categoryId : categoryIds)
            {
                if(categoryEntity.getCategoryId().equals(categoryId))
                {
                    categoryNames.add(categoryEntity.getCategoryName());
                    break;
                }
            }
        }

        return categoryNames;
    }

    public List<CategoryEntity> getSpecificCategoryData(List<Long> categoryIds) {

        List<CategoryEntity> categoryEntityList = categoryRepo.findAll();

        List<CategoryEntity> categoryEntityList1 = new ArrayList<>();

        for(CategoryEntity categoryEntity : categoryEntityList)
        {
            for(Long categoryId : categoryIds)
            {
                if(categoryEntity.getCategoryId().equals(categoryId))
                {
                    categoryEntityList1.add(categoryEntity);
                    break;
                }
            }
        }

        return categoryEntityList1;
    }
}
