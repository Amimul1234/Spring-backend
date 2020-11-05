package com.owo.OwoDokan.entity.admin_related;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Brands {
    @Id
    private String brand_name;
    @Column(columnDefinition = "LONGTEXT")
    private String brand_image;
    private String category;
}
