package com.brewbox.model.DTOs;

import com.brewbox.model.entity.OrderStatusEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddOrderDTO {

    private Long id;

    private UserDTO user;

    private String additionalInformation;

    private OrderStatusEntity status;

    private List<ProductDTO> products;
}
