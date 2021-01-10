package com.yalco.springsecurity.repository;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserRepository {

    private List<UserDetails> userList;

    public UserRepository() {
        this.userList = new ArrayList<>();
        UserDetails userOne = User.withUsername("bill").password("1234").authorities("ROLE_USER").build();
        UserDetails userAdmin = User.withUsername("john").password("admin").authorities("ROLE_ADMIN").build();
        userList.add(userOne);
        userList.add(userAdmin);
    }

    public Optional<UserDetails> getUserByName(String name){
        return userList
                .stream()
                .filter(u -> u.getUsername().equals(name))
                .findFirst();
    }
}
