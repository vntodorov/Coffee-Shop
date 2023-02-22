package com.brewbox.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "comments")
public class CommentEntity extends BaseEntity{

    @Column(columnDefinition = "TEXT")
    private String text;

    @ManyToOne
    private ProductEntity product;

    @ManyToOne
    private UserEntity user;
}
