package com.owo.OwoDokan.service.shop_keeper_related.Debt;

import com.owo.OwoDokan.entity.admin_related.Shops;
import com.owo.OwoDokan.entity.shopKeeper_related.UserDebts;
import com.owo.OwoDokan.entity.shopKeeper_related.User_debt_details;
import com.owo.OwoDokan.repository.admin_related.ShopRepository;
import com.owo.OwoDokan.repository.shop_keeper_related.Debt.UserDebt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShopUserDebt {
    private final UserDebt userDebt;
    private final ShopRepository shopRepository;

    public ShopUserDebt(UserDebt userDebt, ShopRepository shopRepository) {
        this.userDebt = userDebt;
        this.shopRepository = shopRepository;
    }

    public ResponseEntity addDebt(UserDebts userDebts, String shop_mobile_number) {

        Shops shops = shopRepository.getByPhone(shop_mobile_number);

        List<UserDebts> userDebts1 = shops.getUserDebts();

        for(UserDebts userDebts2 : userDebts1)
        {
            if(userDebts2.getUser_mobile_number().equals(userDebts.getUser_mobile_number()))
            {
                return new ResponseEntity(HttpStatus.CONFLICT);
            }
        }

        shops.getUserDebts().add(userDebts);
        userDebts.setShops(shops);
        shopRepository.save(shops);
        return new ResponseEntity(HttpStatus.OK);

    }

    public ResponseEntity addDebtDetails(User_debt_details user_debt_details, Long user_id) {

        UserDebts userDebts1;

        try {
            userDebts1 = userDebt.findByUserId(user_id);
        }catch (Exception e)
        {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        user_debt_details.setUserDebts(userDebts1);

        double debt = userDebts1.getUser_total_debt();

        debt = debt + user_debt_details.getTaka();

        userDebts1.setUser_total_debt(debt);

        userDebts1.getUserDebtDetails().add(user_debt_details);

        try {
            userDebt.save(userDebts1);
            return new ResponseEntity(HttpStatus.CREATED);
        }catch (Exception e)
        {
            return new ResponseEntity(HttpStatus.FAILED_DEPENDENCY);
        }

    }

    public ResponseEntity deleteAdebtDetails(long id_of_debt_details, long user_id) {

        UserDebts userDebts1;

        try
        {
            userDebts1 = userDebt.findByUserId(user_id);

        }catch (Exception e)
        {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        List<User_debt_details> userDebtDetailsList = new ArrayList<>();

        userDebtDetailsList.addAll(userDebts1.getUserDebtDetails());

        double debt = userDebts1.getUser_total_debt();

        for(User_debt_details user_debt_details : userDebtDetailsList)
        {
            if(user_debt_details.getId() == id_of_debt_details)
            {
                userDebtDetailsList.remove(user_debt_details);
                debt = debt - user_debt_details.getTaka();
                break;
            }
        }

        userDebts1.setUser_total_debt(debt);
        userDebts1.getUserDebtDetails().clear();
        userDebts1.getUserDebtDetails().addAll(userDebtDetailsList);

        try
        {
            userDebt.save(userDebts1);
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception e)
        {
            return new ResponseEntity(HttpStatus.FAILED_DEPENDENCY);
        }

    }

    public ResponseEntity getAllDebtDetails(Long user_id) {

        UserDebts userDebts1;

        try
        {
            userDebts1 = userDebt.findByUserId(user_id);

            return new ResponseEntity<UserDebts>(userDebts1, HttpStatus.OK);

        }catch (Exception e)
        {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity getDebtDetailsForACustomer(Long user_id) {
        UserDebts userDebts1;

        try
        {
            userDebts1 = userDebt.findByUserId(user_id);

            if(userDebts1.getUserDebtDetails().size() > 0)
            {
                return new ResponseEntity<List<User_debt_details>>(userDebts1.getUserDebtDetails(), HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }


        }catch (Exception e)
        {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    public List<User_debt_details> getAllDebtDetailsViaList(Long user_id)
    {
        UserDebts userDebts1;

        try
        {
            userDebts1 = userDebt.findByUserId(user_id);
            return userDebts1.getUserDebtDetails();
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public String getCustomerName(Long user_id) {
        return userDebt.findByUserId(user_id).getUser_name();
    }

    public ResponseEntity updateAdebtDetails(User_debt_details user_debt_details, long user_id) {

        UserDebts userDebts;
        int length;
        double debt;

        try {
            userDebts = userDebt.findByUserId(user_id);

            length = userDebts.getUserDebtDetails().size();
            debt = userDebts.getUser_total_debt();

            for(int i=0; i<length; i++)
            {
                if(user_debt_details.getId() == userDebts.getUserDebtDetails().get(i).getId())
                {
                    debt = debt - userDebts.getUserDebtDetails().get(i).getTaka() + user_debt_details.getTaka();
                    userDebts.setUser_total_debt(debt);
                    userDebts.getUserDebtDetails().set(i, user_debt_details);
                    userDebt.save(userDebts);
                    return new ResponseEntity(HttpStatus.OK);
                }
            }

            return new ResponseEntity(HttpStatus.NOT_FOUND);

        }catch (Exception e)
        {
            return new ResponseEntity(HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public ResponseEntity clearAllDebtDetails(Long user_id) {

        UserDebts userDebts;

        try
        {
            userDebts = userDebt.findByUserId(user_id);

            try
            {
                userDebt.delete(userDebts);
                return new ResponseEntity(HttpStatus.OK);
            }catch (Exception e)
            {
                return new ResponseEntity(HttpStatus.FAILED_DEPENDENCY);
            }

        }catch (Exception e)
        {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity getAllDebts(int page, String shop_mobile_number) {

        int pageSize = 10;

        Shops shop = null;

        int fromIndex = page * pageSize;

        try {
            shop  = shopRepository.getByPhone(shop_mobile_number);
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        if (shop != null) {

            int size = shop.getUserDebts().size();

            if(fromIndex >= size)
            {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            else
            {
                return new ResponseEntity<>(shop.getUserDebts().subList(fromIndex, Math.min(fromIndex + pageSize, shop.getUserDebts().size())), HttpStatus.OK);
            }
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
