package com.brewbox.init;

import com.brewbox.model.entity.UserRoleEntity;
import com.brewbox.model.entity.enums.UserRoleEnum;
import com.brewbox.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final UserRoleRepository userRoleRepository;

    @Autowired
    public ConsoleRunner(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRoleRepository.count() == 0) {
            for (UserRoleEnum roleEnum : UserRoleEnum.values()) {
                UserRoleEntity role = new UserRoleEntity();
                role.setRole(roleEnum);
                userRoleRepository.save(role);
            }
        }
    }
}
