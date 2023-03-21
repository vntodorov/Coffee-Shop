package com.brewbox.model.entity;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;


@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
