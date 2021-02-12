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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    @Column(nullable = false)
    private String categoryName;
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String categoryImage;

    @OneToMany(
            targetEntity = SubCategoryEntity.class,
            mappedBy = "categoryEntity",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JsonManagedReference
    private List<SubCategoryEntity> subCategoryEntities = new ArrayList<>();

}
