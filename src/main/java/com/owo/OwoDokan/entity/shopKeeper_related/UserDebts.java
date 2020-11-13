package com.owo.OwoDokan.entity.shopKeeper_related;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.owo.OwoDokan.entity.admin_related.Shops;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class UserDebts implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_id;

    private String user_name;
    private String user_mobile_number;
    private double user_total_debt;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private Shops shops;

    @OneToMany(
            targetEntity = User_debt_details.class,
            mappedBy = "userDebts",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )

    @JsonManagedReference
    private List<User_debt_details> userDebtDetails = new ArrayList<>();
}
