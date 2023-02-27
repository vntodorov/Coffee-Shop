package com.brewbox.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
