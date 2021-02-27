package com.owo.OwoDokan.service.admin_related;

import com.owo.OwoDokan.entity.admin_related.OffersEntity;
import com.owo.OwoDokan.exceptions.NoOffersException;
import com.owo.OwoDokan.repository.adminRelated.OfferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final Logger logger = LoggerFactory.getLogger(OfferService.class);

    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }


    public String addANewOffer(OffersEntity offersEntity) {
        try
        {
            offerRepository.save(offersEntity);
            return "Offer saved successfully";
        }
        catch (Exception e)
        {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    public String updateExistenceOffer(OffersEntity offersEntityBody) {

        Optional<OffersEntity> offersEntity = offerRepository.findById(offersEntityBody.getOfferId());

        if(offersEntity.isPresent())
        {
            OffersEntity offersEntity1 = offersEntity.get();

            offersEntity1.setOfferId(offersEntityBody.getOfferId());
            offersEntity1.setOffer_start_date(offersEntityBody.getOffer_start_date());
            offersEntity1.setOffer_end_date(offersEntityBody.getOffer_end_date());
            offersEntity1.setOffer_is_for(offersEntityBody.getOffer_is_for());
            //offersEntity1.setCategory(offersEntityBody.getCategory());
            offersEntity1.setOffer_image(offersEntityBody.getOffer_image());

            try
            {
                offerRepository.save(offersEntity1);
                return "Offer updated successfully";
            }
            catch (Exception e)
            {
                logger.error("Can not update offer information, "+e);
                return "Offer info can not be updated";
            }
        }
        else
        {
            return "Selected offer does not exists";
        }
    }

    public String deleteOffer(Long offerId) {

        Optional<OffersEntity> offersEntity = offerRepository.findById(offerId);

        if(offersEntity.isPresent())
        {
            try
            {
                OffersEntity offersEntity1 = offersEntity.get();
                offerRepository.delete(offersEntity1);
                return "Offer deleted successfully";
            }catch (Exception e)
            {
                logger.error("Can not delete offer, "+e);
                throw new RuntimeException(e);
            }
        }
        else
        {
            throw new NoOffersException();
        }
    }

    public List<OffersEntity> getAllOffers() {

        List<OffersEntity> offersEntityList = offerRepository.findAll();

        if(!offersEntityList.isEmpty())
        {
            return offersEntityList;
        }
        else
        {
            throw new NoOffersException();
        }
    }
}
