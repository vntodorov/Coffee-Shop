package com.brewbox.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "products")
public class ProductEntity extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private BigDecimal weight;

    @Column(nullable = false)
    private LocalDate added;

    @ManyToOne(fetch = FetchType.EAGER)
    private CategoryEntity category;

    @ManyToOne(fetch = FetchType.EAGER)
    private BrandEntity brand;
}
