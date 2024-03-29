package com.brewbox.model.DTOs;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Getter
@Setter
public class CommentDTO {

    private Long id;

    @NotBlank
    private String text;

    private ProductDTO product;

    private UserDTO user;

    private LocalDate date;

}
