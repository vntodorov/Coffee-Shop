package com.brewbox.web;

import com.brewbox.model.entity.UserEntity;
import com.brewbox.service.ShoppingCartService;
import com.brewbox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class ShoppingCartRestController {

    private final ShoppingCartService shoppingCartService;

    private final UserService userService;

    @Autowired
    public ShoppingCartRestController(ShoppingCartService shoppingCartService, UserService userService) {
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
    }

    @PostMapping("/cart/add/{pid}/{qty}")
    public String addProductToCart(@PathVariable("pid") Long productId,
                                   @PathVariable("qty") int quantity,
                                   @AuthenticationPrincipal UserDetails userDetails){

        UserEntity user = userService.getCurrentUser(userDetails);

        Integer addedQuantity = shoppingCartService.addProduct(productId, quantity, user);

        return addedQuantity + " item(s) of this product were added to your shopping cart.";
    }

    @PostMapping("/cart/update/{pid}/{qty}")
    public String updateQuantity(@PathVariable("pid") Long productId,
                                   @PathVariable("qty") int quantity,
                                   @AuthenticationPrincipal UserDetails userDetails){

        UserEntity user = userService.getCurrentUser(userDetails);

        BigDecimal subtotal = shoppingCartService.updateQuantity(productId, quantity, user);

        return String.valueOf(subtotal);
    }
}
