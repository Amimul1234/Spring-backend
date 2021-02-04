package com.owo.OwoDokan.schedule;

import com.owo.OwoDokan.entity.admin_related.OffersEntity;
import com.owo.OwoDokan.repository.adminRelated.OfferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ApplicationTaskSchedule {

    private static final Logger log = LoggerFactory.getLogger(ApplicationTaskSchedule.class);

    private final OfferRepository offerRepository;

    public ApplicationTaskSchedule(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }


    //Scheduling task for every 5 hours
    @Scheduled(fixedRate = 1000*60*60*5)
    public void reportCurrentTime() {
        updateOfferState();//check for order state decisions
    }

    private void updateOfferState() {
        java.sql.Date today = new java.sql.Date(new java.util.Date().getTime());

        List<OffersEntity> offersEntityList = offerRepository.findAll();

        if(!offersEntityList.isEmpty())
        {
            for(OffersEntity offersEntity : offersEntityList)
            {
                int value = today.compareTo(offersEntity.getOffer_start_date());

                if(value==0 || value > 0) //If it is already past the start date then enable offer
                {
                    offersEntity.setEnabled(true);
                    try
                    {
                        offerRepository.save(offersEntity);
                    }catch (Exception e)
                    {
                        log.error("Can not update offer, which id is: " + String.valueOf(offersEntity.getOfferId()));
                    }
                }

                if(today.after(offersEntity.getOffer_end_date()))
                {
                    offersEntity.setEnabled(false);

                    try
                    {
                        offerRepository.save(offersEntity);
                    }
                    catch (Exception e)
                    {
                        log.error("Can not update offer state, id is: "+String.valueOf(offersEntity.getOfferId()));
                    }
                }
            }
        }
    }
}