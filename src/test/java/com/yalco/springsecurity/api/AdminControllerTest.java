package com.yalco.springsecurity.api;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpSession;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer.sharedHttpSession;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebAppConfiguration
class AdminControllerTest {
    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private FilterChainProxy filterChain;

    private MockMvc mockMvc;

    @BeforeAll
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(sharedHttpSession())
                .addFilter(filterChain).build();
    }

//    @Test
//    public void testJSessionId() throws Exception {
//        MvcResult result = mockMvc.perform(
//                get("http://localhost:8080/api/admin")
//                        .with(httpBasic("Stan","1234")))
//                .andReturn();
//        int b=6;
//    }
    @Test
    public void testJSessionId() throws Exception {
        MvcResult result = mockMvc.perform(
                get("http://localhost:8080/api/admin")
                . with(httpBasic("Stan","1234")))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();

        HttpSession session =result.getRequest()
                .getSession();
        assertNotNull(session);


        //change session id

        HttpSession fakeSession = new MockHttpSession(null,"bla%%");
        HttpSession session2 = mockMvc.perform(get("http://localhost:8080/api/admin").session((MockHttpSession)fakeSession).locale(Locale.ENGLISH))
                .andExpect(status().isOk())
        .andReturn().getRequest().getSession();
//        assertEquals(session.getId(),session2.getId());
    }
}