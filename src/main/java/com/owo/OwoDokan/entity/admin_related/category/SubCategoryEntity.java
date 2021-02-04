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
    private Long sub_category_id;
    @Column(nullable = false)
    private String sub_category_name;
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String sub_category_image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private CategoryEntity categoryEntity;
}
