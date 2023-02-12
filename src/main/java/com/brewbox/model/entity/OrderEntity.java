package com.brewbox.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class OrderEntity extends BaseEntity{

    @ManyToOne
    private UserEntity user;

    @ManyToMany
    @JoinTable(name = "orders_products",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"))
    private List<ProductEntity> product;

    @Column(name = "additional_information", columnDefinition = "TEXT")
    private String additionalInformation;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne
    private OrderStatusEntity status;


}
