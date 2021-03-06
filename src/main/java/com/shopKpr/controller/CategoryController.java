package com.shopKpr.controller;

import com.shopKpr.entity.admin_related.category.CategoryEntity;
import com.shopKpr.service.admin_related.category.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/addNewCategory")
    public String addNewCategory(@RequestBody CategoryEntity categoryEntity)
    {
        return categoryService.addNewCategory(categoryEntity);
    }

    @GetMapping("/getAllCategories")
    public List<CategoryEntity> getAllCategories()
    {
        return categoryService.getAllCategories();
    }

    @GetMapping("/getCategoryBasedOnId")
    public CategoryEntity getCategoryBasedOnId(@RequestParam(name = "categoryId") Long categoryId)
    {
        return categoryService.getCategoryByID(categoryId);
    }

    @GetMapping("/getCategoryListBasedOnId")
    public List<String> getCategoryListBasedOnId(@RequestParam(name = "categoryIds") List<Long> categoryIds)
    {
        return categoryService.getCategoriesByIds(categoryIds);
    }

    @PutMapping("/updateCategory")
    public String updateCategory(@RequestParam(name = "categoryId") Long categoryId, @RequestBody CategoryEntity categoryEntity)
    {
        return categoryService.updateCategory(categoryId, categoryEntity);
    }

    @DeleteMapping("/deleteCategory")
    public String deleteCategory(@RequestParam(name = "categoryId") Long categoryId)
    {
        return categoryService.deleteCategory(categoryId);
    }

    @GetMapping("/getSpecificCategoryData")
    public List<CategoryEntity> getSpecificCategoryData(@RequestParam("categoryIds") List<Long> categoryIds)
    {
        return categoryService.getSpecificCategoryData(categoryIds);
    }
}
