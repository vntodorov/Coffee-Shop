package com.brewbox.model.DTOs;

import com.brewbox.model.entity.enums.OrderStatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OrderDTO {

    private Long id;

    private UserDTO user;

    private String additionalInformation;

    private LocalDate date;

    private OrderStatusEnum status;

}
