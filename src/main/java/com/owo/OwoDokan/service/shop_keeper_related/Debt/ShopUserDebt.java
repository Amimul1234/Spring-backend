package com.owo.OwoDokan.service.shop_keeper_related.Debt;

import com.owo.OwoDokan.entity.shopKeeper_related.UserDebts;
import com.owo.OwoDokan.entity.shopKeeper_related.User_debt_details;
import com.owo.OwoDokan.repository.shop_keeper_related.Debt.UserDebt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopUserDebt {
    private final UserDebt userDebt;

    public ShopUserDebt(UserDebt userDebt) {
        this.userDebt = userDebt;
    }

    public UserDebts addDebt(UserDebts userDebts) {
        UserDebts userDebts1 = userDebt.findByUserMobileNumber(userDebts.getUser_mobile_number());
        if(userDebts1 == null)
        {
            return userDebt.save(userDebts);
        }
        else
            return userDebts1;
    }

    public void addDebtDetails(User_debt_details user_debt_details, String mobile_number) {
        UserDebts userDebts1 = userDebt.findByUserMobileNumber(mobile_number);

        user_debt_details.setUserDebts(userDebts1);

        userDebts1.getUserDebtDetails().add(user_debt_details);

        userDebt.save(userDebts1);
    }

    public void deleteAdebtDetails(long id_of_debt_details, String mobile_number) {
        UserDebts userDebts1 = userDebt.findByUserMobileNumber(mobile_number);

        for(User_debt_details user_debt_details : userDebts1.getUserDebtDetails())
        {
            if(user_debt_details.getId() == id_of_debt_details)
            {
                userDebts1.getUserDebtDetails().remove(user_debt_details);
                userDebt.save(userDebts1);
                return;
            }
        }
    }

    public List<User_debt_details> getAllDebtDetails(String mobile_number) {
        UserDebts userDebts1 = userDebt.findByUserMobileNumber(mobile_number);
        return userDebts1.getUserDebtDetails();
    }
}
