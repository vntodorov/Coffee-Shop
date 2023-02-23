package com.brewbox.service;

import com.brewbox.model.DTOs.UserDTO;
import com.brewbox.model.entity.UserEntity;
import com.brewbox.model.entity.UserRoleEntity;

import static com.brewbox.model.entity.enums.UserRoleEnum.*;

import com.brewbox.repository.UserRepository;
import com.brewbox.repository.UserRoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final ModelMapper mapper;

    @Autowired
    public AdminService(UserRepository userRepository, UserRoleRepository userRoleRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.mapper = mapper;
    }

    public List<UserDTO> getAllNonAdmins() {
        return userRepository.
                findAll().
                stream().
                filter(u -> !u.getRoles().contains(adminRole()))
                .map(this::mapToUserDTO)
                .toList();
    }

    public List<UserDTO> getAllAdmins(UserDetails userDetails) {
        return userRepository.
                findAll().
                stream().
                filter(u -> u.getRoles().contains(adminRole()))
                .filter(u -> !u.getUsername().equals(userDetails.getUsername()))
                .map(this::mapToUserDTO)
                .toList();
    }

    public void addAdmin(Long id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            user.addRole(adminRole());
            user.removeRole(moderatorRole());
            userRepository.save(user);
        }
    }

    public void removeAdmin(Long id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            user.removeRole(adminRole());
            userRepository.save(user);
        }
    }

    private UserRoleEntity adminRole() {
        return userRoleRepository.findByRole(ADMIN).orElseThrow();
    }

    private UserRoleEntity moderatorRole() {
        return userRoleRepository.findByRole(MODERATOR).orElseThrow();
    }

    private UserDTO mapToUserDTO(UserEntity userEntity) {
        return mapper.map(userEntity, UserDTO.class);
    }
}
