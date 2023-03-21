package com.brewbox.model.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class OrderEntity extends BaseEntity {

    @ManyToOne
    private UserEntity user;

    @ManyToMany
    @JoinTable(name = "orders_products",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"))
    private List<ProductEntity> products = new ArrayList<>();

    @Column(name = "additional_information", columnDefinition = "TEXT")
    private String additionalInformation;

    @Column(nullable = false)
    private final LocalDate date = LocalDate.now();

    @ManyToOne
    private OrderStatusEntity status;

    @Column
    private BigDecimal price;

    public void addProductToOrder(ProductEntity product) {
        if (this.products == null){
            this.products = new ArrayList<>();
        }
        this.products.add(product);
    }


}
