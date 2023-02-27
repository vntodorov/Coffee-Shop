package com.brewbox.repository;

import com.brewbox.model.entity.CartItemEntity;
import com.brewbox.model.entity.ProductEntity;
import com.brewbox.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {

    List<CartItemEntity> findByUser(UserEntity user);

    CartItemEntity findByUserAndProduct(UserEntity user, ProductEntity product);
}
