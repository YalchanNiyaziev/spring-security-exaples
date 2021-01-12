package com.yalco.springsecurity.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.authentication.LdapAuthenticator;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;

@Configuration
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //for postman to not save jsession cookie
        http.authorizeRequests()
                .mvcMatchers("/api/admin").hasAnyRole("ADMIN")
                .mvcMatchers("/api/reports").hasAnyRole("USER")
                .anyRequest().permitAll();
    }

    @Bean
    public ContextSource contextSource() {
        return new DefaultSpringSecurityContextSource("ldap://localhost:8389/dc=example,dc=com");
    }

    @Bean
    public LdapAuthenticator bindAuthenticator(BaseLdapPathContextSource contextSource) {
        FilterBasedLdapUserSearch filterBasedLdapUserSearch =
                new FilterBasedLdapUserSearch("ou=people","(uid={0})",contextSource);

        BindAuthenticator bindAuthenticator = new BindAuthenticator(contextSource);
        bindAuthenticator.setUserSearch(filterBasedLdapUserSearch);
        return bindAuthenticator;
    }

    //Authorization
    @Bean
    public LdapAuthoritiesPopulator ldapAuthoritiesPopulator(BaseLdapPathContextSource contextSource){
        String groupSearchBase = " ou=groups"; //authorization entry/base
        DefaultLdapAuthoritiesPopulator authorities =
                new DefaultLdapAuthoritiesPopulator(contextSource, groupSearchBase);
        authorities.setGroupSearchFilter("uniqueMember={0}"); //authorization attribute
        return authorities;
    }

    @Bean
    public LdapAuthenticationProvider authenticationProvider(LdapAuthenticator ldapAuthenticator, LdapAuthoritiesPopulator ldapPopulator){
        return new LdapAuthenticationProvider(ldapAuthenticator,ldapPopulator);
    }
}
