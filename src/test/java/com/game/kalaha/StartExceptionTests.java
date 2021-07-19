package com.game.kalaha;

import com.game.kalaha.web.dto.GameInit;
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

import static com.game.kalaha.web.error.ErrorConstants.StartExceptionMessages.WRONG_NO_OF_PITS_MSG;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StartExceptionTests {
    private static final URI START_URI = URI.create("/api/v1/kalaha/start");

    @Autowired
    MockMvc mockMvc;

    @Test
    public void startTest3() throws Exception {
        GameInit gameInit = new GameInit(CommonMethods.Numbers.ZERO, CommonMethods.Numbers.SEVEN);
        String inputMockGameInit = CommonMethods.mapToJson(gameInit);
        RequestBuilder req = post(START_URI).contentType(MediaType.APPLICATION_JSON)
                .content(inputMockGameInit);
        MvcResult mvcResult = mockMvc.perform(req)
                .andExpect(content().string(containsString(WRONG_NO_OF_PITS_MSG)))
                .andExpect(status().isConflict())
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        Assert.assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
    }
}
