package com.shopKpr.entity.admin_related;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shopKpr.entity.admin_related.category.SubCategoryEntity;
import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Brands{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brandId;
    @Column(nullable = false)
    private String brandName;
    @Column(columnDefinition = "LONGTEXT")
    private String brandImage;

    @ManyToOne
    @JsonBackReference
    private SubCategoryEntity subCategoryEntity;

    @OneToMany(mappedBy = "brands",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH},
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @JsonIgnore
    private List<OwoProduct> owoProductList = new ArrayList<>();
}
