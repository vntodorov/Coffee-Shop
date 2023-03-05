package com.brewbox.model.DTOs;

import com.brewbox.model.entity.enums.OrderStatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class OrderDTO {

    private Long id;

    private UserDTO user;

    private String additionalInformation;

    private BigDecimal price;

    private LocalDate date;

    private OrderStatusEnum status;

}
