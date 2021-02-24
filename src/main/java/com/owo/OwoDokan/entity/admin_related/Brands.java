package com.owo.OwoDokan.entity.admin_related;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.owo.OwoDokan.entity.admin_related.category.SubCategoryEntity;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Brands implements Serializable {
    @Id
    private String brandName;
    @Column(columnDefinition = "LONGTEXT")
    private String brandImage;

    @ManyToOne
    @JsonBackReference
    private SubCategoryEntity subCategoryEntity;
}
