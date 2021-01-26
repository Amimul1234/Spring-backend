package com.owo.OwoDokan.service.admin_related;

import com.owo.OwoDokan.entity.admin_related.OffersEntity;
import com.owo.OwoDokan.repository.admin_related.OfferRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class OfferService {
    private final OfferRepository offerRepository;

    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }


    public ResponseEntity addANewOffer(Date offer_start_date, Date offer_end_date, String offerIsFor, String offer_image, String offer_category) {

        OffersEntity offersEntity = new OffersEntity(offer_start_date, offer_end_date, offerIsFor, offer_image, offer_category);

        try
        {
            offerRepository.save(offersEntity);
            return ResponseEntity.status(HttpStatus.OK).body("Offer saved successfully");
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("Can not create offer");
        }

    }
}
