package com.brewbox.model.DTOs;

import com.brewbox.model.entity.enums.CategoryEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ProductDTO {

    @NotBlank
    @Size(min = 2, max = 20)
    private String name;

    @NotBlank
    @Size(min = 5)
    private String description;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    @Positive
    private BigDecimal weight;

    private LocalDate added = LocalDate.now();

    @NotNull
    private CategoryEnum categoryName;

    @NotBlank
    private String brandName;

    @NotBlank
    private String imageUrl;

    private List<CommentDTO> comments;
}
