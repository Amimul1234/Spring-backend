package com.owo.OwoDokan.entity.admin_related.category;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SubCategory")
public class SubCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer subCategoryId;
    @Column(nullable = false)
    private String subCategoryName;
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String subCategoryImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private CategoryEntity categoryEntity;
}
