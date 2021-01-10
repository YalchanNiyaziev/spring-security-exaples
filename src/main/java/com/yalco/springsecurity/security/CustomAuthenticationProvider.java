package com.yalco.springsecurity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Collection;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService customUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        if(isAuthenticated(authentication,userDetails)){
            return new Authentication() {
                @Override
                public Collection<? extends GrantedAuthority> getAuthorities() {
                    return userDetails.getAuthorities();
                }

                @Override
                public Object getCredentials() {
                    return userDetails.getPassword();
                }

                @Override
                public Object getDetails() {
                    return null;
                }

                @Override
                public Object getPrincipal() {
                    return (Principal) () -> userDetails.getUsername();
                }

                @Override
                public boolean isAuthenticated() {
                    return true;
                }

                @Override
                public void setAuthenticated(boolean b) throws IllegalArgumentException {

                }

                @Override
                public String getName() {
                    return userDetails.getUsername();
                }
            };
        } else {
            throw new AuthenticationCredentialsNotFoundException("Bad credentials");
        }
    }

    @Override
    public boolean supports(Class<?> authClassType) {
        return UsernamePasswordAuthenticationToken.class.equals(authClassType);
    }
    private boolean isAuthenticated(Authentication authCredentials, UserDetails user){
        String password = String.valueOf( authCredentials.getCredentials());
        return passwordEncoder.matches(user.getPassword(),password);
    }
}
