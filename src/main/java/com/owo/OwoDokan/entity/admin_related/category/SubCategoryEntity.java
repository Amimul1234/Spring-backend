package com.owo.OwoDokan.entity.admin_related.category;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.owo.OwoDokan.entity.admin_related.Brands;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class SubCategoryEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sub_category_id;
    @Column(nullable = false)
    private String sub_category_name;
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String sub_category_image;

    @ManyToOne
    @JsonBackReference
    private CategoryEntity categoryEntity;

    @OneToMany(
            mappedBy = "subCategoryEntity",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH},
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<Brands> brandsList = new ArrayList<>();
}
