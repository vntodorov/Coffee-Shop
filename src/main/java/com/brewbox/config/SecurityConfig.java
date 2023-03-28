package com.brewbox.config;

import com.brewbox.model.entity.enums.UserRoleEnum;
import com.brewbox.repository.UserRepository;
import com.brewbox.service.BrewboxUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity,
                                           SecurityContextRepository securityContextRepository) throws Exception {

//        httpSecurity.
//                    authorizeHttpRequests().
//                    requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll().
//                    antMatchers("/", "/users/login", "/users/register").permitAll().
//                    antMatchers("/cart").authenticated().
//                    antMatchers("/brands/**").permitAll().
//                    antMatchers("/products/**").permitAll().
//                    antMatchers("/maintenance").permitAll().
//                    anyRequest().
//                    authenticated().
//                and().
//                    formLogin().
//                    loginPage("/users/login").
//                    usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY).
//                    passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY).
//                    defaultSuccessUrl("/").
//                    failureForwardUrl("/users/login-error").
//                and().
//                    logout().
//                    logoutUrl("/users/logout").
//                    logoutSuccessUrl("/").
//                    invalidateHttpSession(true).
//                    deleteCookies("JSESSIONID");
        httpSecurity.
                authorizeHttpRequests().
                requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll().
                requestMatchers("/", "/users/login", "/users/register", "/users/login-error").permitAll().
                requestMatchers("/admins/**", "/moderators/**").hasRole(UserRoleEnum.ADMIN.name()).
                anyRequest().authenticated().
                and().
                formLogin().
                loginPage("/users/login").
                usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY).
                passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY).
                defaultSuccessUrl("/", true).
                failureForwardUrl("/users/login-error").
                and().
                logout().
                logoutUrl("/users/logout").
                logoutSuccessUrl("/").
                invalidateHttpSession(true).
                and().
                securityContext().
                securityContextRepository(securityContextRepository);

        return httpSecurity.build();
    }

    @Primary
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new BrewboxUserDetailsService(userRepository);
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new DelegatingSecurityContextRepository(
                new RequestAttributeSecurityContextRepository(),
                new HttpSessionSecurityContextRepository()
        );
    }


}
