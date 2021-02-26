package com.owo.OwoDokan.entity.admin_related;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.owo.OwoDokan.entity.admin_related.category.CategoryEntity;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table
public class OffersEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long offerId;
    @Column(nullable = false)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "MMM dd, yyyy hh:mm:ss a", timezone = "Asia/Dhaka")
    private Date offer_start_date;
    @Column(nullable = false)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "MMM dd, yyyy hh:mm:ss a", timezone = "Asia/Dhaka")
    private Date offer_end_date;
    @Column(nullable = false)
    private String offer_is_for;
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String offer_image;
    @Column(nullable = false)
    private boolean enabled;

    @ManyToOne
    @JsonBackReference
    private CategoryEntity categoryEntity;


    public OffersEntity() {
    }

    public OffersEntity(Long offerId, Date offer_start_date, Date offer_end_date,
                        String offer_is_for, String offer_image, boolean enabled, CategoryEntity categoryEntity) {
        this.offerId = offerId;
        this.offer_start_date = offer_start_date;
        this.offer_end_date = offer_end_date;
        this.offer_is_for = offer_is_for;
        this.offer_image = offer_image;
        this.enabled = enabled;
        this.categoryEntity = categoryEntity;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public Date getOffer_start_date() {
        return offer_start_date;
    }

    public void setOffer_start_date(Date offer_start_date) {
        this.offer_start_date = offer_start_date;
    }

    public Date getOffer_end_date() {
        return offer_end_date;
    }

    public void setOffer_end_date(Date offer_end_date) {
        this.offer_end_date = offer_end_date;
    }

    public String getOffer_is_for() {
        return offer_is_for;
    }

    public void setOffer_is_for(String offer_is_for) {
        this.offer_is_for = offer_is_for;
    }

    public String getOffer_image() {
        return offer_image;
    }

    public void setOffer_image(String offer_image) {
        this.offer_image = offer_image;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public CategoryEntity getCategoryEntity() {
        return categoryEntity;
    }

    public void setCategoryEntity(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
    }
}
