package com.yalco.springsecurity.security;

import com.yalco.springsecurity.entity.User;
import com.yalco.springsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(s);

        User foundUser = user.orElseThrow(()->new UsernameNotFoundException("User with this username not found"));

        return new SecurityUser(foundUser);
    }
}
