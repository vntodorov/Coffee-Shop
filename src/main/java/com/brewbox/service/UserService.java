package com.brewbox.service;

import com.brewbox.model.DTOs.UserRegisterDTO;
import com.brewbox.model.entity.UserEntity;
import com.brewbox.model.entity.UserRoleEntity;

import static com.brewbox.model.entity.enums.UserRoleEnum.*;

import com.brewbox.repository.UserRepository;
import com.brewbox.repository.UserRoleRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;


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


    public void registerUser(UserRegisterDTO userRegisterDTO,
                                 Consumer<Authentication> successfulLoginProcessor) {
        UserEntity userToRegister = mapper.map(userRegisterDTO, UserEntity.class);
        userToRegister.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));

        userToRegister.addRole(clientRole());

        userRepository.save(userToRegister);

        UserDetails userDetails = userDetailsService.loadUserByUsername(userToRegister.getEmail());

        Authentication auth =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        userDetails.getPassword(),
                        userDetails.getAuthorities()
                );

        successfulLoginProcessor.accept(auth);

    }

    public UserEntity getCurrentUser(UserDetails userDetails) {
        return userRepository.findByUsername(userDetails.getUsername()).orElseThrow();

    }

    private UserRoleEntity clientRole() {
        return userRoleRepository.
                findByRole(CLIENT).
                orElseThrow();
    }
}
