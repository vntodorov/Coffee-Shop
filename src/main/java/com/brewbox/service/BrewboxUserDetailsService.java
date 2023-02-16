package com.brewbox.service;

import com.brewbox.model.entity.UserEntity;
import com.brewbox.model.entity.UserRoleEntity;
import com.brewbox.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class BrewboxUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private final ModelMapper mapper;

    @Autowired
    public BrewboxUserDetailsService(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public UserDetails loadUserByUsername(String registerAttribute) throws UsernameNotFoundException {

        if (userRepository.findByUsername(registerAttribute).isPresent()){
            mapper.map(userRepository.findByUsername(registerAttribute).get())
        } else if (userRepository.findByEmail(registerAttribute).isPresent()) {
            return null;
        } else {
            throw new UsernameNotFoundException("User with username/email " + registerAttribute + " not found!");
        }

    }

    private UserDetails mapToUser(UserEntity userEntity) {

        return new BrewboxUserDetails(
                userEntity.getId(),
                userEntity.getPassword(),
                userEntity.getEmail(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.
                        getRoles().
                        stream().
                        map(this::mapRole).
                        toList()
        );
    }

    private GrantedAuthority mapRole(UserRoleEntity userRole) {
        return new SimpleGrantedAuthority("ROLE_" +
                userRole.
                        getRole().name());
    }
}
