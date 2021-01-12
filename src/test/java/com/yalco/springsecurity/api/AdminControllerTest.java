package com.yalco.springsecurity.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Calling endpoint for admin role without auth should unauthorized")
    public void testAdminNoAuthAccess() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/admin")).andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Calling endpoint for admin role with credential should ok")
    public void testAdminAuthAccess() throws Exception {
        mockMvc.perform(
                get("http://localhost:8080/api/admin")
                        .with(httpBasic("admin","adminPass")))
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("Calling endpoint for admin  with user with not privileged role")
    public void testAdminAccessWithUserNotPrivileged() throws Exception {
        mockMvc.perform(
                get("http://localhost:8080/api/admin")
                        .with(httpBasic("user","userPass")))
                .andExpect(status().isForbidden());
    }
    @Test
    @DisplayName("Calling endpoint for admin  incorrect password should unauthorized")
    public void testAdminAuthAccessIncorrectPass() throws Exception {
        mockMvc.perform(
                get("http://localhost:8080/api/admin")
                        .with(httpBasic("admin","xxx")))
                .andExpect(status().isUnauthorized());
    }
    @Test
    @DisplayName("Calling endpoint for admin  incorrect password should unauthorized")
    public void testAdminAuthAccessIncorrectUsernameCorrectPass() throws Exception {
        mockMvc.perform(
                get("http://localhost:8080/api/admin")
                        .with(httpBasic("admins","adminPass")))
                .andExpect(status().isUnauthorized());
    }
    @Test
    @DisplayName("Calling endpoint for reports with admin valid credentials should ok")
    public void testReportsAdminAuth() throws Exception {
        mockMvc.perform(
                get("http://localhost:8080/api/reports")
                        .with(httpBasic("admin","adminPass")))
                .andExpect(status().isOk());
    }
}