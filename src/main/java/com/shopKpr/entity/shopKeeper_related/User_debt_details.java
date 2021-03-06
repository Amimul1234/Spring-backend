package com.shopKpr.entity.shopKeeper_related;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class User_debt_details implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(columnDefinition = "LONGTEXT")
    private String description;
    private String date;
    private double taka;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private UserDebts userDebts;
}
