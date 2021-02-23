package com.owo.OwoDokan.entity.admin_related;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.owo.OwoDokan.entity.admin_related.category.SubCategoryEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private SubCategoryEntity subCategoryEntity;
}
