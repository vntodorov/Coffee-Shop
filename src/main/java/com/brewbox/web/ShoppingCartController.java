package com.brewbox.web;

import com.brewbox.model.entity.UserEntity;
import com.brewbox.service.ShoppingCartService;
import com.brewbox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    private final UserService userService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService, UserService userService) {
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
    }

    @GetMapping
    public String showShoppingCart(Model model,
                                   @AuthenticationPrincipal UserDetails userDetails){

        UserEntity user = userService.getCurrentUser(userDetails);
        model.addAttribute("cartItems", shoppingCartService.getCartItems(user));

        return "shopping-cart";
    }
}
