package com.brewbox.web;

import com.brewbox.model.DTOs.AddOrderDTO;
import com.brewbox.model.DTOs.CartItemDTO;
import com.brewbox.model.entity.OrderEntity;
import com.brewbox.model.entity.UserEntity;
import com.brewbox.service.ShoppingCartService;
import com.brewbox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

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

    @ModelAttribute("cartItemDTO")
    public CartItemDTO cartItemDTO() {
        return new CartItemDTO();
    }

    @ModelAttribute("addOrderDTO")
    public AddOrderDTO addOrderDTO() {
        return new AddOrderDTO();
    }

    @GetMapping
    public String showShoppingCart(Model model,
                                   @AuthenticationPrincipal UserDetails userDetails) {

        UserEntity user = userService.getCurrentUser(userDetails);
        model.addAttribute("cartItems", shoppingCartService.getCartItems(user));

        return "shopping-cart";
    }

    @PostMapping("/add/product/{pid}")
    public String addProductToCart(@Valid CartItemDTO cartItemDTO,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes,
                                   @PathVariable("pid") Long pid,
                                   @AuthenticationPrincipal UserDetails userDetails) {

        if (!shoppingCartService.addProduct(cartItemDTO, pid, userDetails)) {
            redirectAttributes.addFlashAttribute("unsuccessful", true);
        } else {
            redirectAttributes.addFlashAttribute("successful", true);

        }

        return "redirect:/product/{pid}";
    }

    @PostMapping("/delete/product/{pid}")
    public String removeProductFromCart(@PathVariable("pid") Long pid,
                                        @AuthenticationPrincipal UserDetails userDetails) {

        shoppingCartService.removeProductFromCart(pid, userDetails);

        return "redirect:/cart";


    }

    @GetMapping("/delete/product/{pid}")
    public String invalidDeleteProductFromCart() {
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String invalidCartCheckout() {
        return "redirect:/";
    }

    @PostMapping("/checkout")
    public String cartCheckout(AddOrderDTO addOrderDTO,
                               @AuthenticationPrincipal UserDetails userDetails,
                               Model model) {

        OrderEntity order = shoppingCartService.makeOrder(addOrderDTO, userDetails);
        model.addAttribute("order", order);

        return "shopping-cart-checkout";
    }
}
