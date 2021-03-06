package com.shopKpr.controller;

import com.shopKpr.entity.admin_related.category.SubCategoryEntity;
import com.shopKpr.service.admin_related.category.SubCategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SubCategoryController {
    private final SubCategoryService subCategoryService;

    public SubCategoryController(SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }

    @PostMapping("/addNewSubCategory")
    public String addNewSubCategory(@RequestParam(name = "categoryId") Long categoryId, @RequestBody SubCategoryEntity subCategoryEntity)
    {
        return subCategoryService.addNewSubCategory(categoryId, subCategoryEntity);
    }

    @GetMapping("/getAllSubCategories")
    public List<SubCategoryEntity> getAllSubCategories(@RequestParam(name = "categoryId") Long categoryId)
    {
        return subCategoryService.getAllSubCategories(categoryId);
    }

    @PutMapping("/updateSubCategory")
    public String updateSubCategory(@RequestParam(name = "categoryId") Long categoryId, @RequestBody SubCategoryEntity subCategoryEntity)
    {
        return subCategoryService.updateSubCategory(categoryId, subCategoryEntity);
    }

    @DeleteMapping("/deleteSubCategory")
    public String deleteSubCategory(@RequestParam(name = "subCategoryId") Long subCategoryId)
    {
        return subCategoryService.deleteSubCategory(subCategoryId);
    }
}
