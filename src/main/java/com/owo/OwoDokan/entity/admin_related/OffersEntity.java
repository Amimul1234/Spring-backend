package com.owo.OwoDokan.entity.admin_related;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class OffersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer offerId;
    @Column(nullable = false)
    private Date offer_start_date;
    @Column(nullable = false)
    private Date offer_end_date;
    @Column(nullable = false)
    private String offer_is_for;
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String offer_image;
    @Column(nullable = false)
    private String category;

    public OffersEntity() {
    }

    public OffersEntity(Date offer_start_date, Date offer_end_date, String offer_is_for, String offer_image, String category) {
        this.offer_start_date = offer_start_date;
        this.offer_end_date = offer_end_date;
        this.offer_is_for = offer_is_for;
        this.offer_image = offer_image;
        this.category = category;
    }

    public Integer getOfferId() {
        return offerId;
    }

    public void setOfferId(Integer offerId) {
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
}
