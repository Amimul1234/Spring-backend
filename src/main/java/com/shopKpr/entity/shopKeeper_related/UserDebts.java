package com.shopKpr.entity.shopKeeper_related;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shopKpr.entity.admin_related.Shops;
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH
            , CascadeType.REFRESH})
    @JsonBackReference
    private Shops shops;

    @OneToMany(
            mappedBy = "userDebts",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH
            , CascadeType.REFRESH}
    )
    @JsonIgnore
    private List<User_debt_details> userDebtDetails = new ArrayList<>();
}
