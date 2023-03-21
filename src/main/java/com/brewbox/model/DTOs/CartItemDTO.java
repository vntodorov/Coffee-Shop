package com.brewbox.model.DTOs;


import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
public class CartItemDTO {

    private UserDTO user;

    private ProductDTO product;

    @NotNull
    private int quantity;

    @Transient
    public BigDecimal getSubtotal(){
        return this.product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

}
