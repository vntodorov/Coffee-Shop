package com.brewbox.model.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CommentDTO {

    private String text;

    private ProductDTO product;

    private UserDTO user;

    private LocalDate date;

}
