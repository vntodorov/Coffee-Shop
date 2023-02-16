package com.brewbox.service;

import com.brewbox.model.DTOs.UserRegisterDTO;
import com.brewbox.model.entity.UserEntity;
import com.brewbox.model.entity.UserRoleEntity;
import static com.brewbox.model.entity.enums.UserRoleEnum.*;
import com.brewbox.repository.UserRepository;
import com.brewbox.repository.UserRoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final ModelMapper mapper;

    @Autowired
    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.mapper = mapper;
    }


    public void registerAndLogin(UserRegisterDTO userRegisterDTO) {
        UserEntity userToRegister = mapper.map(userRegisterDTO, UserEntity.class);
        userToRegister.addRole(clientRole());

        //TODO: encode password

        userRepository.save(userToRegister);
        login(userToRegister.getEmail());

    }

    private void login(String registerAttribute) {

    }

    private UserRoleEntity clientRole(){
        return userRoleRepository.
                findByRole(CLIENT.name()).
                orElseThrow();
    }
}
