package com.brewbox.service;

import com.brewbox.model.DTOs.UserDTO;
import com.brewbox.model.entity.UserEntity;
import com.brewbox.model.entity.UserRoleEntity;
import com.brewbox.repository.UserRepository;
import com.brewbox.repository.UserRoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.brewbox.model.entity.enums.UserRoleEnum.*;

@Service
public class ModeratorService {

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final ModelMapper mapper;

    @Autowired
    public ModeratorService(UserRepository userRepository, UserRoleRepository userRoleRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.mapper = mapper;
    }

    public List<UserDTO> getAllNonModerators() {
        return userRepository.
                findAll().
                stream().
                filter(u -> u.getRoles().contains(clientRole())).
                filter(u -> !u.getRoles().contains(adminRole())).
                filter(u -> !u.getRoles().contains(moderatorRole())).
                map(this::mapToUserDTO).
                toList();
    }

    public void addModerator(Long id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            user.addRole(moderatorRole());
            userRepository.save(user);
        }
    }

    public List<UserDTO> getAllModerators(UserDetails userDetails) {
        return userRepository.
                findAll().
                stream().
                filter(u -> u.getRoles().contains(moderatorRole())).
                filter(u -> !u.getUsername().equals(userDetails.getUsername())).
                map(this::mapToUserDTO).
                toList();
    }

    public void removeModerator(Long id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            user.removeRole(moderatorRole());
            userRepository.save(user);
        }
    }

    private UserRoleEntity moderatorRole() {
        return userRoleRepository.findByRole(MODERATOR).orElseThrow();
    }

    private UserRoleEntity adminRole() {
        return userRoleRepository.findByRole(ADMIN).orElseThrow();
    }

    private UserRoleEntity clientRole() {
        return userRoleRepository.findByRole(CLIENT).orElseThrow();
    }

    private UserDTO mapToUserDTO(UserEntity userEntity) {
        return mapper.map(userEntity, UserDTO.class);
    }
}
