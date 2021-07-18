package com.game.kalaha;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.kalaha.web.controller.KalahaController;
import com.game.kalaha.web.dto.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

//@SpringBootTest(classes = KalahaController.class)
//@RunWith(SpringRunner.class)
//@WebMvcTest(KalahaController.class)
//@ContextConfiguration(classes = KalahaController.class)

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class KalahaApplicationTests {

    private static final URI START_URI = URI.create("/api/v1/kalaha/start");

    @Autowired
    MockMvc mockMvc;

//    @Test
//    void contextLoads() {
//    }

    @Test
    public void signupTest() throws Exception {
        Map<Player, PlayerArea> playerMap = new HashMap<>();
        GameInit gameInit = new GameInit(6,6);
//        Player player1 = new Player("Player1", true);

//        playerMap.put(player1, generatePlayerArea(player1, gameInit));
//        Player player2 = new Player("Player2", false);
//        playerMap.put(player2, generatePlayerArea(player2, gameInit));
//        Board board = new Board(playerMap);
//        signUpUserRequest.setEmail("my@email.com");


        RequestBuilder req = get(START_URI).contentType(MediaType.APPLICATION_JSON);

//        this.mockMvc.perform(req).andDo(print())
//            .andExpect(content().string(containsString("\"id\":1")));

        MvcResult mvcResult = this.mockMvc.perform(req).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());


//        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(START_URI)
//                .contentType(MediaType.APPLICATION_JSON).content(exampleFruitJson)).andReturn();
//
//        MockHttpServletResponse response = mvcResult.getResponse();
//        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());






//        SignUpUserRequest signUpUserRequest = new SignUpUserRequest();
//        signUpUserRequest.setEmail("my@email.com");
//        signUpUserRequest.setUsername("username");
//        signUpUserRequest.setPassword("123456");
//        signUpUserRequest.setType("msisdn"); //email , basic, msisdn
//        signUpUserRequest.setMsisdn("999999999");
//        signUpUserRequest.setServiceId(2L);
//
//
//        RequestBuilder req = post(SIGNUP_URI).
//            contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(signUpUserRequest));
//
//        this.mockMvc.perform(req).andDo(print()).andExpect(content().string(containsString("\"responseCode\":0")))
//            .andDo(document("sso-signup"));
    }


}

