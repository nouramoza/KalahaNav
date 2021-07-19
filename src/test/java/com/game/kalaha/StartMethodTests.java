package com.game.kalaha;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.game.kalaha.web.dto.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StartMethodTests {
    private static final URI START_URI = URI.create("/api/v1/kalaha/start");

    @Autowired
    MockMvc mockMvc;

    public static class TestOne {
        private static final int NO_OF_PITS = 6;
        private static final int NO_OF_STONES_PER_PIT = 6;
    }

    public static class TestTwo {
        private static final int NO_OF_PITS = 6;
        private static final int NO_OF_STONES_PER_PIT = 4;
    }


    @Test
    public void startTest1() throws Exception {
        GameInit gameInit = new GameInit(TestOne.NO_OF_PITS, TestOne.NO_OF_STONES_PER_PIT);
        String inputMockGameInit = CommonMethods.mapToJson(gameInit);

        String outputExpectedMockBoard = CommonMethods.makeBoard(gameInit);
        RequestBuilder req = post(START_URI).contentType(MediaType.APPLICATION_JSON)
                .content(inputMockGameInit);


        MvcResult mvcResult = mockMvc.perform(req)
                .andExpect(content().string(containsString(outputExpectedMockBoard)))
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void startTest2() throws Exception {
        GameInit gameInit = new GameInit(TestTwo.NO_OF_PITS, TestTwo.NO_OF_STONES_PER_PIT);
        String inputMockGameInit = CommonMethods.mapToJson(gameInit);

        String outputExpectedMockBoard = CommonMethods.makeBoard(gameInit);
        RequestBuilder req = post(START_URI).contentType(MediaType.APPLICATION_JSON)
                .content(inputMockGameInit);


        MvcResult mvcResult = mockMvc.perform(req)
                .andExpect(content().string(containsString(outputExpectedMockBoard)))
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

        @Test
        public void startTest3() throws Exception {
            GameInit gameInit = new GameInit(CommonMethods.Numbers.TEN, CommonMethods.Numbers.SEVEN);
            String inputMockGameInit = CommonMethods.mapToJson(gameInit);

            String outputExpectedMockBoard = CommonMethods.makeBoard(gameInit);
            RequestBuilder req = post(START_URI).contentType(MediaType.APPLICATION_JSON)
                    .content(inputMockGameInit);


            MvcResult mvcResult = mockMvc.perform(req)
                    .andExpect(content().string(containsString(outputExpectedMockBoard)))
                    .andReturn();
            MockHttpServletResponse response = mvcResult.getResponse();
            Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
        }



}
