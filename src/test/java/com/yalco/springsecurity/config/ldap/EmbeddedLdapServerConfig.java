package com.yalco.springsecurity.config.ldap;

import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.sdk.LDAPException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Configuration
public class EmbeddedLdapServerConfig {

    @Value("classpath:test.ldif")
    private Resource ldifResource;

    @Bean(destroyMethod = "close")
    public InMemoryDirectoryServer inMemoryDirectoryServer() throws LDAPException, IOException {
        InMemoryDirectoryServerConfig config =new InMemoryDirectoryServerConfig("dc=example,dc=com");
        InMemoryDirectoryServer inMemoryDirectoryServer = new InMemoryDirectoryServer(config);
        inMemoryDirectoryServer.importFromLDIF(true,ldifResource.getFile().getPath());
        inMemoryDirectoryServer.startListening();
        return inMemoryDirectoryServer;
    }

}
