package com.brewbox.model.DTOs;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CartItemDTO {

    @NotNull
    private UserDTO user;

    @NotNull
    private ProductDTO product;

    @NotNull
    private int quantity;
}
