package com.brewbox.service;

import com.brewbox.model.DTOs.UserRegisterDTO;
import com.brewbox.model.entity.UserEntity;
import com.brewbox.model.entity.UserRoleEntity;
import static com.brewbox.model.entity.enums.UserRoleEnum.*;
import com.brewbox.repository.UserRepository;
import com.brewbox.repository.UserRoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper mapper;

    @Autowired
    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }


    public void registerAndLogin(UserRegisterDTO userRegisterDTO) {
        UserEntity userToRegister = mapper.map(userRegisterDTO, UserEntity.class);
        userToRegister.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));

        userToRegister.addRole(clientRole());

        userRepository.save(userToRegister);
        login(userToRegister.getEmail());

    }

    private void login(String registerAttribute) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(registerAttribute);

        Authentication auth =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        userDetails.getPassword(),
                        userDetails.getAuthorities()
                );

        SecurityContextHolder.
                getContext().
                setAuthentication(auth);
    }

    private UserRoleEntity clientRole(){
        return userRoleRepository.
                findByRole(CLIENT).
                orElseThrow();
    }
}
