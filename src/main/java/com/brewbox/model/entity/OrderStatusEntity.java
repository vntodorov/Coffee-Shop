package com.brewbox.model.entity;

import com.brewbox.model.entity.enums.OrderStatusEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "orders_statuses")
public class OrderStatusEntity extends BaseEntity{

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;


}
