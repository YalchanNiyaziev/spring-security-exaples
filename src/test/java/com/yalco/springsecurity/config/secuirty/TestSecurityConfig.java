package com.yalco.springsecurity.config.secuirty;

import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class TestSecurityConfig extends WebSecurityConfigurerAdapter {

    private final InMemoryDirectoryServer inMemoryDirectoryServer;

    public TestSecurityConfig(InMemoryDirectoryServer inMemoryDirectoryServer) {
        this.inMemoryDirectoryServer = inMemoryDirectoryServer;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println("yalco port:"+inMemoryDirectoryServer.getListenPort());
        String ldapServerUrl = "ldap://localhost:"+inMemoryDirectoryServer.getListenPort()+"/dc=example,dc=com";
        System.out.println(ldapServerUrl);

        auth.ldapAuthentication()
            .contextSource()
            .url(ldapServerUrl)
            .and()
            .userSearchBase("ou=people")
            .userSearchFilter("uid={0}")
            .groupSearchBase("ou=groups")
            .groupSearchFilter("uniqueMember={0}");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.authorizeRequests()
                .mvcMatchers("/api/admin").hasRole("ADMIN")
                .mvcMatchers("/api/reports").hasRole("USER")
                .anyRequest().authenticated();
    }
}
