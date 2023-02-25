package com.brewbox.web;

import com.brewbox.model.DTOs.OrderDTO;
import com.brewbox.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/change-status")
    public String changeOrderStatus(Model model){
        model.addAttribute("orders", orderService.getAllOrders());
        return "orders-change-status";
    }

    @PutMapping("/change-status")
    public String changeOrderStatus(@Valid OrderDTO orderDTO){




        return null;
    }
}
