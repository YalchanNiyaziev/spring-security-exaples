package com.yalco.springsecurity.security.config;

import com.yalco.springsecurity.entity.User;
import com.yalco.springsecurity.repository.UserRepository;
import com.yalco.springsecurity.security.model.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@Configuration
public class UserDetailsServiceConfig {

    @Autowired
    private UserRepository userRepository;


    @Bean
    public UserDetailsService userDetailsService(){
        return s -> {
            Optional<User> user = userRepository.findByUsername(s);

            User foundUser = user.orElseThrow(()->new UsernameNotFoundException("User with this username not found"));

            return new SecurityUser(foundUser);
        };
    }
}
