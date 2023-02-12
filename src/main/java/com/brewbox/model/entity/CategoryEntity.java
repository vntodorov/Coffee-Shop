package com.brewbox.model.entity;

import com.brewbox.model.entity.enums.CategoryEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "categories")
public class CategoryEntity extends BaseEntity {

    @Column(nullable = false)
    @Enumerated
    private CategoryEnum category;
}
