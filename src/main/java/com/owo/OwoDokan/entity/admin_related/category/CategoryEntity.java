package com.owo.OwoDokan.entity.admin_related.category;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Category")
public class CategoryEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    @Column(nullable = false)
    private String categoryName;
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String categoryImage;

    @OneToMany(
            mappedBy = "categoryEntity",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH},
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JsonManagedReference
    private List<SubCategoryEntity> subCategoryEntities = new ArrayList<>();

    public CategoryEntity() {
    }

    public CategoryEntity(Long categoryId, String categoryName, String categoryImage, List<SubCategoryEntity> subCategoryEntities) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryImage = categoryImage;
        this.subCategoryEntities = subCategoryEntities;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public List<SubCategoryEntity> getSubCategoryEntities() {
        return subCategoryEntities;
    }

    public void setSubCategoryEntities(List<SubCategoryEntity> subCategoryEntities) {
        this.subCategoryEntities = subCategoryEntities;
    }
}
