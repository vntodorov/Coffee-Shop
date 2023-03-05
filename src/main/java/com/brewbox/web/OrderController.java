package com.brewbox.web;

import com.brewbox.model.DTOs.OrderDTO;
import com.brewbox.model.entity.UserEntity;
import com.brewbox.service.OrderService;
import com.brewbox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    private final UserService userService;

    @Autowired
    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @ModelAttribute("orderDTO")
    public OrderDTO orderDTO() {
        return new OrderDTO();
    }

    @GetMapping
    public String showUserOrders(Model model,
                                 @AuthenticationPrincipal UserDetails userDetails) {
        UserEntity user = userService.getCurrentUser(userDetails);

        List<OrderDTO> orders = orderService.getUserOrders(user);

        model.addAttribute("orders", orders);

        return "orders";

    }

    @GetMapping("/change/status")
    public String changeOrderStatus(Model model) {
        model.addAttribute("orders", orderService.getAllNotDeliveredOrders());
        return "orders-change-status";
    }

    @PostMapping("/change/status/{oid}")
    public String changeOrderStatus(@Valid OrderDTO orderDTO,
                                    @PathVariable("oid") Long oid) {

        orderService.changeOrderStatus(orderDTO, oid);
        return "redirect:/orders/change/status";
    }
}
