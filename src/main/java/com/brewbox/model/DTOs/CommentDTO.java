package com.brewbox.model.DTOs;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class CommentDTO {

    @NotBlank
    private String text;

    @NotNull
    private ProductDTO product;

    @NotNull
    private UserDTO user;

    @NotNull
    private LocalDate date;

}
