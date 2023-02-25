package com.brewbox.service;

import com.brewbox.model.DTOs.OrderDTO;
import com.brewbox.model.entity.OrderEntity;
import com.brewbox.model.entity.enums.OrderStatusEnum;
import com.brewbox.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final ModelMapper mapper;

    @Autowired
    public OrderService(OrderRepository orderRepository, ModelMapper mapper) {
        this.orderRepository = orderRepository;
        this.mapper = mapper;
    }

    public List<OrderDTO> getAllOrders() {
        return orderRepository.
                findAll().
                stream().
                filter(o -> !o.getStatus().getStatus().equals(OrderStatusEnum.DELIVERED)).
                map(this::mapToOrderDTO).
                toList();
    }

    private OrderDTO mapToOrderDTO(OrderEntity order) {
        return mapper.map(order, OrderDTO.class);
    }
}
