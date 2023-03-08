package com.brewbox.repository;

import com.brewbox.model.entity.BrandEntity;
import com.brewbox.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findByBrand(BrandEntity brand);


}
