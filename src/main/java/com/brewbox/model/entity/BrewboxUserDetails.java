package com.brewbox.model.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class BrewboxUserDetails implements UserDetails {

    private final String username;

    private final String firstName;

    private final String middleName;

    private final String lastName;

    private final String password;

    private final String email;

    private final Collection<GrantedAuthority> authorities;

    public BrewboxUserDetails(String username, String firstName, String middleName, String lastName, String password, String email, Collection<GrantedAuthority> authorities) {
        this.username = username;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.authorities = authorities;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName(){
        StringBuilder fullName = new StringBuilder();

        if (getFirstName() != null){
            fullName.append(getFirstName());
        }

        if (getMiddleName() != null){
            if (!fullName.isEmpty()){
                fullName.append(" ");
            }
            fullName.append(getMiddleName());
        }

        if (getLastName() != null){
            if (!fullName.isEmpty()){
                fullName.append(" ");
            }
            fullName.append(getLastName());
        }

        return fullName.toString();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
