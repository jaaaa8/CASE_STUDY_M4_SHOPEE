package com.example.case_study_mdl_4_shopee.controller.customer;

import com.example.case_study_mdl_4_shopee.entity.Account;
import com.example.case_study_mdl_4_shopee.entity.Orders;
import com.example.case_study_mdl_4_shopee.entity.SubOrders;
import com.example.case_study_mdl_4_shopee.service.impl.ICartService;
import com.example.case_study_mdl_4_shopee.service.impl.IUserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {

    private final ICartService cartService;
    private final IUserManagementService userService;

    @ModelAttribute("userBalance")
    public Long getUserBalance() {
        Account currentAccount = userService.getCurrentAccount();
        return (currentAccount != null) ? currentAccount.getBalance() : 0L;
    }

    @ModelAttribute("cartCount")
    public int getCartCount() {
        Account currentAccount = userService.getCurrentAccount();
        if (currentAccount == null) {
            return 0;
        }
        Orders cart = cartService.getCart(currentAccount.getAccountId());
        if (cart == null || cart.getSubOrders() == null) {
            return 0;
        }
        
        int count = 0;
        for (SubOrders so : cart.getSubOrders()) {
            if (so.getOrderItems() != null) {
                count += so.getOrderItems().size();
            }
        }
        return count;
    }
}
