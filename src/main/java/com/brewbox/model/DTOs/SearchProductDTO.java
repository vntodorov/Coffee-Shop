package com.brewbox.model.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SearchProductDTO {

    private String brandName;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;
}
