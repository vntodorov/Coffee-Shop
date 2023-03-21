package com.brewbox.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "comments")
public class CommentEntity extends BaseEntity {

    @Column(columnDefinition = "TEXT")
    private String text;

    @ManyToOne
    private ProductEntity product;

    @ManyToOne
    private UserEntity user;

    private final LocalDate date = LocalDate.now();
}
