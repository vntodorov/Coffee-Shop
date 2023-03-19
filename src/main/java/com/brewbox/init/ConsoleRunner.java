package com.brewbox.init;

import com.brewbox.model.entity.CategoryEntity;
import com.brewbox.model.entity.OrderStatusEntity;
import com.brewbox.model.entity.UserRoleEntity;
import com.brewbox.model.entity.enums.CategoryEnum;
import com.brewbox.model.entity.enums.OrderStatusEnum;
import com.brewbox.model.entity.enums.UserRoleEnum;
import com.brewbox.repository.CategoryRepository;
import com.brewbox.repository.OrderStatusRepository;
import com.brewbox.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final UserRoleRepository userRoleRepository;

    private final CategoryRepository categoryRepository;

    private final OrderStatusRepository orderStatusRepository;

    @Autowired
    public ConsoleRunner(UserRoleRepository userRoleRepository, CategoryRepository categoryRepository, OrderStatusRepository orderStatusRepository) {
        this.userRoleRepository = userRoleRepository;
        this.categoryRepository = categoryRepository;
        this.orderStatusRepository = orderStatusRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        initOrderStatuses();
    }

    private void initOrderStatuses() {
        if (orderStatusRepository.count() == 0) {
            for (OrderStatusEnum orderStatusEnum : OrderStatusEnum.values()) {
                OrderStatusEntity orderStatus = new OrderStatusEntity();
                orderStatus.setStatus(orderStatusEnum);
                orderStatusRepository.save(orderStatus);
            }
        }
    }
}
