package com.owo.OwoDokan.entity.admin_related.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.owo.OwoDokan.entity.admin_related.OffersEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Category")
public class CategoryEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    @Column(nullable = false)
    private String categoryName;
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String categoryImage;

    @OneToMany(
            mappedBy = "categoryEntity",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH},
            orphanRemoval = true
    )
    @JsonIgnore
    private List<SubCategoryEntity> subCategoryEntities = new ArrayList<>();

    @OneToMany(
            mappedBy = "categoryEntity",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH},
            orphanRemoval = true
    )
    @JsonIgnore
    private List<OffersEntity> offersEntityList = new ArrayList<>();

    public CategoryEntity() {
    }

    public CategoryEntity(Long categoryId, String categoryName, String categoryImage,
                          List<SubCategoryEntity> subCategoryEntities, List<OffersEntity> offersEntityList) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryImage = categoryImage;
        this.subCategoryEntities = subCategoryEntities;
        this.offersEntityList = offersEntityList;
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

    public List<OffersEntity> getOffersEntityList() {
        return offersEntityList;
    }

    public void setOffersEntityList(List<OffersEntity> offersEntityList) {
        this.offersEntityList = offersEntityList;
    }
}
