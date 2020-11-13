package com.owo.OwoDokan.service.shop_keeper_related.Debt;

import com.owo.OwoDokan.entity.shopKeeper_related.UserDebts;
import com.owo.OwoDokan.entity.shopKeeper_related.User_debt_details;
import com.owo.OwoDokan.repository.shop_keeper_related.Debt.UserDebt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

        double debt = userDebts1.getUser_total_debt();

        debt = debt + user_debt_details.getTaka();

        userDebts1.setUser_total_debt(debt);

        userDebts1.getUserDebtDetails().add(user_debt_details);

        userDebt.save(userDebts1);
    }

    public void deleteAdebtDetails(long id_of_debt_details, String mobile_number) {

        UserDebts userDebts1 = userDebt.findByUserMobileNumber(mobile_number);

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

        userDebt.save(userDebts1);
    }

    public List<User_debt_details> getAllDebtDetails(String mobile_number) {
        UserDebts userDebts1 = userDebt.findByUserMobileNumber(mobile_number);
        return userDebts1.getUserDebtDetails();
    }

    public String getCustomerName(String mobile_number) {
        return userDebt.findByUserMobileNumber(mobile_number).getUser_name();
    }

    public void updateAdebtDetails(User_debt_details user_debt_details, String mobile_number) {

        UserDebts userDebts = userDebt.findByUserMobileNumber(mobile_number);

        int length = userDebts.getUserDebtDetails().size();

        double debt = userDebts.getUser_total_debt();

        for(int i=0; i<length; i++)
        {
            if(user_debt_details.getId() == userDebts.getUserDebtDetails().get(i).getId())
            {
                debt = debt - userDebts.getUserDebtDetails().get(i).getTaka() + user_debt_details.getTaka();
                userDebts.setUser_total_debt(debt);
                userDebts.getUserDebtDetails().set(i, user_debt_details);
                userDebt.save(userDebts);
                return;
            }
        }
    }

    public void clearAllDebtDetails(String mobile_number) {
        UserDebts userDebts = userDebt.findByUserMobileNumber(mobile_number);
        userDebt.delete(userDebts);
    }
}
