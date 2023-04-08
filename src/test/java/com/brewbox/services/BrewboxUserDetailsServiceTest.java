package com.brewbox.services;

import com.brewbox.model.entity.UserEntity;
import com.brewbox.model.entity.UserRoleEntity;
import com.brewbox.model.entity.enums.UserRoleEnum;
import com.brewbox.repository.UserRepository;
import com.brewbox.service.BrewboxUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentest4j.AssertionFailedError;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BrewboxUserDetailsServiceTest {

    private final String EXISTING_USERNAME = "admin";

    private final String NOT_EXISTING_EMAIL = "non-existent@example.com";

    private BrewboxUserDetailsService toTest;

    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void setUp() {
        toTest = new BrewboxUserDetailsService(mockUserRepository);
    }

    @Test
    void testUserFound() {

        UserRoleEntity testAdminRole = new UserRoleEntity();
        testAdminRole.setRole(UserRoleEnum.ADMIN);

        UserRoleEntity testClientRole = new UserRoleEntity();
        testClientRole.setRole(UserRoleEnum.CLIENT);

        UserEntity testUserEntity = new UserEntity();
        testUserEntity.setUsername(EXISTING_USERNAME);
        testUserEntity.setPassword("12345");
        testUserEntity.setRoles(Set.of(testClientRole, testAdminRole));

        when(mockUserRepository.findByUsername(EXISTING_USERNAME)).thenReturn(Optional.of(testUserEntity));
        UserDetails adminDetails = toTest.loadUserByUsername(EXISTING_USERNAME);

        assertNotNull(adminDetails);
        assertEquals(EXISTING_USERNAME, adminDetails.getUsername());
        assertEquals(testUserEntity.getPassword(), adminDetails.getPassword());

        assertEquals(2, adminDetails.getAuthorities().size());
        assertRole(adminDetails.getAuthorities(), "ROLE_CLIENT");
        assertRole(adminDetails.getAuthorities(), "ROLE_ADMIN");


    }

    private void assertRole(Collection<? extends GrantedAuthority> authorities, String role) {

        authorities.
                stream().
                filter(a -> role.equals(a.getAuthority())).
                findAny().
                orElseThrow(() -> new AssertionFailedError("Role " + role + " not found!"));
    }

    @Test
    void testUserNotFound() {
        assertThrows(UsernameNotFoundException.class, () -> {
            toTest.loadUserByUsername(NOT_EXISTING_EMAIL);
        });
    }
}
