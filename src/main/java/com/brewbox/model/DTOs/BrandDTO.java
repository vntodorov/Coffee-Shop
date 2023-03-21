package com.brewbox.model.DTOs;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandDTO {

    private Long id;

    @NotBlank
    @Size(min = 2, max = 20)
    private String name;

    @NotBlank
    @Size(min = 5)
    private String description;

    @NotBlank
    private String imageUrl;
}
