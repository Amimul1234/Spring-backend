package com.owo.OwoDokan.entity.admin_related.category;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Category")
public class CategoryEntity {
    @Id
    private String category_name;
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String category_image;

    @OneToMany(
            targetEntity = SubCategoryEntity.class,
            mappedBy = "categoryEntity",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<SubCategoryEntity> subCategoryEntities = new ArrayList<>();

}
