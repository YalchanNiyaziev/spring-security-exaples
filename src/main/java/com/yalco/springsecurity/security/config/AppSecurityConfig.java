package com.yalco.springsecurity.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.ldapAuthentication()
                .contextSource()
                    .url("ldap://localhost:8389/dc=example,dc=com")
                .and()
                .userSearchBase("ou=people")
                .userSearchFilter("uid={0}")
                .groupSearchFilter("uniqueMember={0}")
                .groupSearchBase("ou=groups");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //for postman to not save jsession cookie
        http.authorizeRequests()
                .mvcMatchers("/api/admin").hasAnyRole("ADMIN")
                .mvcMatchers("/api/reports").hasAnyRole("USER")
                .anyRequest().permitAll();
    }

}
