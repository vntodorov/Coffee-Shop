package com.brewbox.service;

import com.brewbox.model.DTOs.OrderDTO;
import com.brewbox.model.entity.OrderEntity;
import com.brewbox.model.entity.OrderStatusEntity;
import com.brewbox.model.entity.enums.OrderStatusEnum;
import com.brewbox.repository.OrderRepository;
import com.brewbox.repository.OrderStatusRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderStatusRepository orderStatusRepository;

    private final ModelMapper mapper;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderStatusRepository orderStatusRepository, ModelMapper mapper) {
        this.orderRepository = orderRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.mapper = mapper;
    }

    public List<OrderDTO> getAllNotDeliveredOrders() {
        return orderRepository.
                findAll().
                stream().
                filter(o -> !o.getStatus().getStatus().equals(OrderStatusEnum.DELIVERED)).
                map(this::mapToOrderDTO).
                toList();
    }

    public void changeOrderStatus(OrderDTO orderDTO, Long orderId) {
        OrderStatusEnum orderStatusEnum = orderDTO.getStatus();
        OrderStatusEntity newStatus = findOrderStatus(orderStatusEnum);

        Optional<OrderEntity> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isPresent()) {
            OrderEntity order = optionalOrder.get();
            order.setStatus(newStatus);
            orderRepository.save(order);
        }


    }

    private OrderStatusEntity findOrderStatus(OrderStatusEnum orderStatusEnum) {
        return orderStatusRepository.findByStatus(orderStatusEnum);
    }

    private OrderDTO mapToOrderDTO(OrderEntity order) {
        return mapper.map(order, OrderDTO.class);
    }
}
