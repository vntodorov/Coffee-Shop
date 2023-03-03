package com.brewbox.repository;

import com.brewbox.model.entity.OrderStatusEntity;
import com.brewbox.model.entity.enums.OrderStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatusEntity, Long> {

    OrderStatusEntity findByStatus(OrderStatusEnum status);
}
