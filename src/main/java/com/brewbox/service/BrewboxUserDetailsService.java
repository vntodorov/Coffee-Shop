package com.brewbox.service;

import com.brewbox.model.entity.BrewboxUserDetails;
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

    @Autowired
    public BrewboxUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String registerAttribute) throws UsernameNotFoundException {

        if (userRepository.findByUsername(registerAttribute).isPresent()) {
            return mapToUser(userRepository.findByUsername(registerAttribute).get());
        } else if (userRepository.findByEmail(registerAttribute).isPresent()) {
            return mapToUser(userRepository.findByEmail(registerAttribute).get());
        } else {
            throw new UsernameNotFoundException("User with username/email " + registerAttribute + " not found!");
        }

    }

    private UserDetails mapToUser(UserEntity userEntity) {

        return new BrewboxUserDetails(
                userEntity.getUsername(),
                userEntity.getFirstName(),
                userEntity.getMiddleName(),
                userEntity.getLastName(),
                userEntity.getPassword(),
                userEntity.getEmail(),
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
