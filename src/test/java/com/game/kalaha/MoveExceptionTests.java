package com.game.kalaha;

import com.game.kalaha.web.dto.GameInit;
import com.game.kalaha.web.dto.Pit;
import com.game.kalaha.web.dto.Player;
import com.game.kalaha.web.dto.PlayerArea;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MoveExceptionTests {

    private static final String MOVE_URI = "/api/v1/kalaha/move/{pitNo}";

    @Autowired
    MockMvc mockMvc;

    @Test
    public void moveEmptyPitTest() throws Exception {
        Map<Player, PlayerArea> playerMap = new HashMap<>();
        GameInit gameInit = new GameInit(CommonMethods.DefaultValues.DEFAULT_NO_OF_PITS, CommonMethods.DefaultValues.DEFAULT_NO_OF_STONES);

        Pit[] pits1 = {new Pit(CommonMethods.Numbers.ONE),
                new Pit(CommonMethods.Numbers.ZERO),
                new Pit(CommonMethods.Numbers.EIGHT),
                new Pit(CommonMethods.Numbers.EIGHT),
                new Pit(CommonMethods.Numbers.EIGHT),
                new Pit(CommonMethods.Numbers.EIGHT)};
        Pit[] pits2 = {new Pit(CommonMethods.Numbers.ZERO),
                new Pit(CommonMethods.Numbers.EIGHT),
                new Pit(CommonMethods.Numbers.SEVEN),
                new Pit(CommonMethods.Numbers.SEVEN),
                new Pit(CommonMethods.Numbers.SEVEN),
                new Pit(CommonMethods.Numbers.SEVEN)};

        String inputMockBoard = CommonMethods.makeInputBoard(gameInit,
                pits1, CommonMethods.Numbers.TWO,
                pits2, CommonMethods.Numbers.ONE,
                1);

        Pit[] outPits1 = {new Pit(CommonMethods.Numbers.ZERO),
                new Pit(CommonMethods.Numbers.ZERO),
                new Pit(CommonMethods.Numbers.EIGHT),
                new Pit(CommonMethods.Numbers.EIGHT),
                new Pit(CommonMethods.Numbers.EIGHT),
                new Pit(CommonMethods.Numbers.EIGHT)};
        Pit[] outPits2 = {new Pit(CommonMethods.Numbers.ZERO),
                new Pit(CommonMethods.Numbers.EIGHT),
                new Pit(CommonMethods.Numbers.SEVEN),
                new Pit(CommonMethods.Numbers.SEVEN),
                new Pit(CommonMethods.Numbers.ZERO),
                new Pit(CommonMethods.Numbers.SEVEN)};

        String outputExpectedMockBoard = CommonMethods.makeInputBoard(gameInit,
                outPits1, CommonMethods.Numbers.TEN,
                outPits2, CommonMethods.Numbers.ONE, 2);


        RequestBuilder req = post(MOVE_URI, CommonMethods.Numbers.TWO)
                .contentType(MediaType.APPLICATION_JSON) // for DTO
                .accept(MediaType.APPLICATION_JSON) // for PathVariable
                .content(inputMockBoard);


        //todo: bad request test
        MvcResult mvcResult = this.mockMvc.perform(req)
//                .andExpect(content().string(containsString(outputExpectedMockBoard)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void moveWrongPitTest() throws Exception {
        Map<Player, PlayerArea> playerMap = new HashMap<>();
        GameInit gameInit = new GameInit(CommonMethods.DefaultValues.DEFAULT_NO_OF_PITS, CommonMethods.DefaultValues.DEFAULT_NO_OF_STONES);

        Pit[] pits1 = {new Pit(CommonMethods.Numbers.ONE),
                new Pit(CommonMethods.Numbers.ZERO),
                new Pit(CommonMethods.Numbers.EIGHT),
                new Pit(CommonMethods.Numbers.EIGHT),
                new Pit(CommonMethods.Numbers.EIGHT),
                new Pit(CommonMethods.Numbers.EIGHT)};
        Pit[] pits2 = {new Pit(CommonMethods.Numbers.ZERO),
                new Pit(CommonMethods.Numbers.EIGHT),
                new Pit(CommonMethods.Numbers.SEVEN),
                new Pit(CommonMethods.Numbers.SEVEN),
                new Pit(CommonMethods.Numbers.SEVEN),
                new Pit(CommonMethods.Numbers.SEVEN)};

        String inputMockBoard = CommonMethods.makeInputBoard(gameInit,
                pits1, CommonMethods.Numbers.TWO,
                pits2, CommonMethods.Numbers.ONE,
                1);

        Pit[] outPits1 = {new Pit(CommonMethods.Numbers.ZERO),
                new Pit(CommonMethods.Numbers.ZERO),
                new Pit(CommonMethods.Numbers.EIGHT),
                new Pit(CommonMethods.Numbers.EIGHT),
                new Pit(CommonMethods.Numbers.EIGHT),
                new Pit(CommonMethods.Numbers.EIGHT)};
        Pit[] outPits2 = {new Pit(CommonMethods.Numbers.ZERO),
                new Pit(CommonMethods.Numbers.EIGHT),
                new Pit(CommonMethods.Numbers.SEVEN),
                new Pit(CommonMethods.Numbers.SEVEN),
                new Pit(CommonMethods.Numbers.ZERO),
                new Pit(CommonMethods.Numbers.SEVEN)};

        String outputExpectedMockBoard = CommonMethods.makeInputBoard(gameInit,
                outPits1, CommonMethods.Numbers.TEN,
                outPits2, CommonMethods.Numbers.ONE, 2);


        RequestBuilder req = post(MOVE_URI, CommonMethods.Numbers.SEVEN)
                .contentType(MediaType.APPLICATION_JSON) // for DTO
                .accept(MediaType.APPLICATION_JSON) // for PathVariable
                .content(inputMockBoard);

        //todo: bad request exception
        MvcResult mvcResult = this.mockMvc.perform(req)
                .andExpect(content().string(containsString(outputExpectedMockBoard)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }


}
