package com.owo.OwoDokan.entity.admin_related.registerAccount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(indexes = {@Index(columnList = "mobileNumber", name = "mobileNumber")})
public class ShopKeeperUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shopKeeperId;
    private String name;
    private String mobileNumber;
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String pin;
    @Column(columnDefinition = "LONGTEXT")
    private String imageUri;
    private Boolean accountEnabled;
}
