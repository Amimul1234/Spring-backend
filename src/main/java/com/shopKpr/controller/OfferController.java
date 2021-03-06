package com.shopKpr.controller;

import com.shopKpr.entity.admin_related.OffersEntity;
import com.shopKpr.service.admin_related.OfferService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/getAllOffers")
    public List<OffersEntity> getAllOffers()
    {
        return offerService.getAllOffers();
    }

    @GetMapping("/getBannerForSelectedCategories")
    public List<String> bannerImages(@RequestParam("categoryIds") List<Long> categoryIds)
    {
        return offerService.getOfferImages(categoryIds);
    }

    @PostMapping("/addAnOffer")
    public String addAnOffer(@RequestBody OffersEntity offersEntity)
    {
        return offerService.addANewOffer(offersEntity);
    }

    @PutMapping("/updateAnOffer")
    public String updateAnOffer(@RequestBody OffersEntity offersEntity)
    {
        return offerService.updateExistenceOffer(offersEntity);
    }

    @DeleteMapping("/deleteOffer")
    public String deleteAnOffer(@RequestParam(name = "offerId") Long offerId)
    {
        return offerService.deleteOffer(offerId);
    }

}
