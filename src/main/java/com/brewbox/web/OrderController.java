package com.brewbox.web;

import com.brewbox.model.DTOs.OrderDTO;
import com.brewbox.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ModelAttribute("orderDTO")
    public OrderDTO orderDTO(){
        return new OrderDTO();
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
