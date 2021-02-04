package com.owo.OwoDokan.service.admin_related;

import com.owo.OwoDokan.entity.admin_related.OffersEntity;
import com.owo.OwoDokan.repository.adminRelated.OfferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final Logger logger = LoggerFactory.getLogger(OfferService.class);

    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }


    public ResponseEntity addANewOffer(OffersEntity offersEntity) {

        OffersEntity offersEntity1 = new OffersEntity();

        offersEntity1.setOffer_start_date(offersEntity.getOffer_start_date());
        offersEntity1.setOffer_end_date(offersEntity.getOffer_end_date());
        offersEntity1.setOffer_is_for(offersEntity.getOffer_is_for());
        offersEntity1.setOffer_image(offersEntity.getOffer_image());
        offersEntity1.setCategory(offersEntity.getCategory());
        offersEntity1.setEnabled(offersEntity.isEnabled());

        try
        {
            offerRepository.save(offersEntity1);
            return ResponseEntity.status(HttpStatus.OK).body("Offer saved successfully");
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("Can not create offer");
        }

    }

    public ResponseEntity updateExistenceOffer(OffersEntity offersEntityBody) {

        Optional<OffersEntity> offersEntity = offerRepository.findById(offersEntityBody.getOfferId());

        if(offersEntity.isPresent())
        {
            OffersEntity offersEntity1 = offersEntity.get();

            offersEntity1.setOfferId(offersEntityBody.getOfferId());
            offersEntity1.setOffer_start_date(offersEntityBody.getOffer_start_date());
            offersEntity1.setOffer_end_date(offersEntityBody.getOffer_end_date());
            offersEntity1.setOffer_is_for(offersEntityBody.getOffer_is_for());
            offersEntity1.setCategory(offersEntityBody.getCategory());
            offersEntity1.setOffer_image(offersEntityBody.getOffer_image());

            try
            {
                offerRepository.save(offersEntity1);
                return ResponseEntity.status(HttpStatus.OK).body("Offer updated successfully");
            }
            catch (Exception e)
            {
                logger.error("Can not update offer information, "+e);
                return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("Offer info can not be updated");
            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("Selected offer does not exists");
        }
    }

    public ResponseEntity deleteOffer(Long offer_id) {
        Optional<OffersEntity> offersEntity = offerRepository.findById(offer_id);

        if(offersEntity.isPresent())
        {
            try
            {
                OffersEntity offersEntity1 = offersEntity.get();
                offerRepository.delete(offersEntity1);
                return ResponseEntity.status(HttpStatus.OK).body("Offer deleted successfully");
            }catch (Exception e)
            {
                logger.error("Can not delete offer, "+e);
                return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("Offer can not be deleted");
            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("Offer is not presented");
        }
    }
}
