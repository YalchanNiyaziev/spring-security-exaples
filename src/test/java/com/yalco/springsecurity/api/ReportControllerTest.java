package com.yalco.springsecurity.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.Cookie;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ReportControllerTest {

    @Autowired
    MockMvc mockMvc;

//    @Autowired
//    MockHttpSession session;

    @Autowired
    TestRestTemplate restTemplate;
    @LocalServerPort
    private int port;


    @Test
    @DisplayName("Calling endpoint for reports without auth should unauthorized")
    public void testReportsNoAuthAccess() throws Exception {
        MvcResult mvcResult= mockMvc.perform(get("http://localhost:8080/api/reports")).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        Cookie[] cookies =response.getCookies();
        int b =5;
    }

    @Test
    @DisplayName("Calling endpoint for reports with auth should success")
    public void testReportsBasicAuthAccess() throws Exception {
        MvcResult mvcResult= mockMvc.perform(get("http://localhost:8080/api/reports")
                .with(httpBasic("Stan","1234"))).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
//        MockHttpSession session =
    }

    @Test
    @DisplayName("Testing with Rest template")
    public void testRestTemplate(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Basic U3RhbjoxMjM0");
        RequestEntity requestEntity = new RequestEntity(headers, HttpMethod.GET, URI.create("http://localhost:"+port+"/api/reports"));
        ResponseEntity<String> response = restTemplate.exchange(requestEntity,String.class);
        int statusAfterLogin =response.getStatusCodeValue();
        assertEquals(200,statusAfterLogin);

         List<String> jsessionId = response.getHeaders().get("Set-Cookie");

        String cookieValue = jsessionId.get(0).split(";")[0];

        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Cookie",cookieValue);
        RequestEntity requestEntity2 = new RequestEntity(headers2, HttpMethod.GET, URI.create("http://localhost:"+port+"/api/reports"));
        ResponseEntity<String> response2 = restTemplate.exchange(requestEntity2,String.class);
        int statusAfterSessionId =response2.getStatusCodeValue();
        assertEquals(200,statusAfterSessionId);
    }

}