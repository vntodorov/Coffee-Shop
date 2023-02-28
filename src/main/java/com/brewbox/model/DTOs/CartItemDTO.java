package com.brewbox.model.DTOs;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class CartItemDTO {

    @NotNull
    private UserDTO user;

    @NotNull
    private ProductDTO product;

    @NotNull
    private int quantity;

    @Transient
    public BigDecimal getSubtotal(){
        return this.product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
