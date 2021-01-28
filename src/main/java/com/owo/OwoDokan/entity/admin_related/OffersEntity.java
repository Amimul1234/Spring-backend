package com.owo.OwoDokan.entity.admin_related;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Locale;

@Entity
@Table
public class OffersEntity {
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
    private String category;
    @Column(nullable = false)
    private boolean enabled;

    public OffersEntity() {
    }

    public OffersEntity(Date offer_start_date, Date offer_end_date, String offer_is_for,
                        String offer_image, String category, boolean enabled) {
        this.offer_start_date = offer_start_date;
        this.offer_end_date = offer_end_date;
        this.offer_is_for = offer_is_for;
        this.offer_image = offer_image;
        this.category = category;
        this.enabled = enabled;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
