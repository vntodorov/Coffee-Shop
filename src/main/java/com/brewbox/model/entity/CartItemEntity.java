package com.brewbox.model.entity;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "cart_items")
public class CartItemEntity extends BaseEntity {

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private ProductEntity product;

    private int quantity;


}
