package com.brewbox.model.DTOs;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ProductDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    @Positive
    private BigDecimal weight;

    private LocalDate added = LocalDate.now();

    @NotNull
    private CategoryDTO category;

    @NotNull
    private BrandDTO brand;

    @NotBlank
    private String imageUrl;

    private List<CommentDTO> comments;
}
