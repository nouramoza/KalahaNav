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

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MoveMethodTests {

    private static final String MOVE_URI = "/api/v1/kalaha/move/{pitNo}";

    @Autowired
    MockMvc mockMvc;

    public static class TestOne {
        private static final int PLAYER1_PIT_0 = 6;
        private static final int PLAYER1_PIT_1 = 6;
        private static final int PLAYER1_PIT_2 = 6;
        private static final int PLAYER1_PIT_3 = 6;
        private static final int PLAYER1_PIT_4 = 6;
        private static final int PLAYER1_PIT_5 = 6;
        private static final int PLAYER1_BOWL = 0;

        private static final int PLAYER2_PIT_0 = 6;
        private static final int PLAYER2_PIT_1 = 6;
        private static final int PLAYER2_PIT_2 = 6;
        private static final int PLAYER2_PIT_3 = 6;
        private static final int PLAYER2_PIT_4 = 6;
        private static final int PLAYER2_PIT_5 = 6;
        private static final int PLAYER2_BOWL = 0;

        private static final int SELECTED_PIT_NO = 1;
    }

    @Test
    public void moveTest1() throws Exception {
        Map<Player, PlayerArea> playerMap = new HashMap<>();
        GameInit gameInit = new GameInit(CommonMethods.DefaultValues.DEFAULT_NO_OF_PITS, CommonMethods.DefaultValues.DEFAULT_NO_OF_STONES);

        Pit[] pits1 = {new Pit(TestOne.PLAYER1_PIT_0),
                new Pit(TestOne.PLAYER1_PIT_1),
                new Pit(TestOne.PLAYER1_PIT_2),
                new Pit(TestOne.PLAYER1_PIT_3),
                new Pit(TestOne.PLAYER1_PIT_4),
                new Pit(TestOne.PLAYER1_PIT_5)};
        Pit[] pits2 = {new Pit(TestOne.PLAYER2_PIT_0),
                new Pit(TestOne.PLAYER2_PIT_1),
                new Pit(TestOne.PLAYER2_PIT_2),
                new Pit(TestOne.PLAYER2_PIT_3),
                new Pit(TestOne.PLAYER2_PIT_4),
                new Pit(TestOne.PLAYER2_PIT_5)};

        String inputMockBoard = CommonMethods.makeInputBoard(gameInit,
                pits1, TestOne.PLAYER1_BOWL,
                pits2, TestOne.PLAYER2_BOWL,
                1);

        Pit[] outPits1 = {new Pit(CommonMethods.Numbers.ZERO),
                new Pit(CommonMethods.Numbers.SEVEN),
                new Pit(CommonMethods.Numbers.SEVEN),
                new Pit(CommonMethods.Numbers.SEVEN),
                new Pit(CommonMethods.Numbers.SEVEN),
                new Pit(CommonMethods.Numbers.SEVEN)};
        Pit[] outPits2 = {new Pit(TestOne.PLAYER2_PIT_0),
                new Pit(TestOne.PLAYER2_PIT_1),
                new Pit(TestOne.PLAYER2_PIT_2),
                new Pit(TestOne.PLAYER2_PIT_3),
                new Pit(TestOne.PLAYER2_PIT_4),
                new Pit(TestOne.PLAYER2_PIT_5)};

        String outputExpectedMockBoard = CommonMethods.makeInputBoard(gameInit,
                outPits1, CommonMethods.Numbers.ONE,
                outPits2, TestOne.PLAYER2_BOWL, 1);


        RequestBuilder req = post(MOVE_URI, TestOne.SELECTED_PIT_NO)
                .contentType(MediaType.APPLICATION_JSON) // for DTO
                .accept(MediaType.APPLICATION_JSON) // for PathVariable
                .content(inputMockBoard);

        MvcResult mvcResult = this.mockMvc.perform(req)
                .andExpect(content().string(containsString(outputExpectedMockBoard)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
//        MockHttpServletResponse response = mvcResult.getResponse();
//        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void moveTest2() throws Exception {
        Map<Player, PlayerArea> playerMap = new HashMap<>();
        GameInit gameInit = new GameInit(CommonMethods.DefaultValues.DEFAULT_NO_OF_PITS, CommonMethods.DefaultValues.DEFAULT_NO_OF_STONES);

        Pit[] pits1 = {new Pit(CommonMethods.Numbers.ZERO),
                new Pit(CommonMethods.Numbers.SEVEN),
                new Pit(CommonMethods.Numbers.SEVEN),
                new Pit(CommonMethods.Numbers.SEVEN),
                new Pit(CommonMethods.Numbers.SEVEN),
                new Pit(CommonMethods.Numbers.SEVEN)};
        Pit[] pits2 = {new Pit(CommonMethods.Numbers.SIX),
                new Pit(CommonMethods.Numbers.SIX),
                new Pit(CommonMethods.Numbers.SIX),
                new Pit(CommonMethods.Numbers.SIX),
                new Pit(CommonMethods.Numbers.SIX),
                new Pit(CommonMethods.Numbers.SIX)};

        String inputMockBoard = CommonMethods.makeInputBoard(gameInit,
                pits1, CommonMethods.Numbers.ONE,
                pits2, CommonMethods.Numbers.ZERO,
                1);

        Pit[] outPits1 = {new Pit(CommonMethods.Numbers.ZERO),
                new Pit(CommonMethods.Numbers.ZERO),
                new Pit(CommonMethods.Numbers.EIGHT),
                new Pit(CommonMethods.Numbers.EIGHT),
                new Pit(CommonMethods.Numbers.EIGHT),
                new Pit(CommonMethods.Numbers.EIGHT)};
        Pit[] outPits2 = {new Pit(CommonMethods.Numbers.SEVEN),
                new Pit(CommonMethods.Numbers.SEVEN),
                new Pit(CommonMethods.Numbers.SIX),
                new Pit(CommonMethods.Numbers.SIX),
                new Pit(CommonMethods.Numbers.SIX),
                new Pit(CommonMethods.Numbers.SIX)};

        String outputExpectedMockBoard = CommonMethods.makeInputBoard(gameInit,
                outPits1, CommonMethods.Numbers.TWO,
                outPits2, CommonMethods.Numbers.ZERO, 2);


        RequestBuilder req = post(MOVE_URI, CommonMethods.Numbers.TWO)
                .contentType(MediaType.APPLICATION_JSON) // for DTO
                .accept(MediaType.APPLICATION_JSON) // for PathVariable
                .content(inputMockBoard);

        MvcResult mvcResult = this.mockMvc.perform(req)
                .andExpect(content().string(containsString(outputExpectedMockBoard)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void moveCapturePitTest() throws Exception {
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


        RequestBuilder req = post(MOVE_URI, CommonMethods.Numbers.ONE)
                .contentType(MediaType.APPLICATION_JSON) // for DTO
                .accept(MediaType.APPLICATION_JSON) // for PathVariable
                .content(inputMockBoard);

        MvcResult mvcResult = this.mockMvc.perform(req)
                .andExpect(content().string(containsString(outputExpectedMockBoard)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void moveGameOverTest() throws Exception {
        Map<Player, PlayerArea> playerMap = new HashMap<>();
        GameInit gameInit = new GameInit(CommonMethods.DefaultValues.DEFAULT_NO_OF_PITS, CommonMethods.DefaultValues.DEFAULT_NO_OF_STONES);

        Pit[] pits1 = {new Pit(CommonMethods.Numbers.ZERO),
                new Pit(CommonMethods.Numbers.ZERO),
                new Pit(CommonMethods.Numbers.ZERO),
                new Pit(CommonMethods.Numbers.ZERO),
                new Pit(CommonMethods.Numbers.ZERO),
                new Pit(CommonMethods.Numbers.ONE)};
        Pit[] pits2 = {new Pit(CommonMethods.Numbers.ZERO),
                new Pit(CommonMethods.Numbers.ZERO),
                new Pit(CommonMethods.Numbers.ZERO),
                new Pit(CommonMethods.Numbers.ONE),
                new Pit(CommonMethods.Numbers.ZERO),
                new Pit(CommonMethods.Numbers.ONE)};

        String inputMockBoard = CommonMethods.makeInputBoard(gameInit,
                pits1, 37,
                pits2, 32,
                1);

        Pit[] outPits1 = {new Pit(CommonMethods.Numbers.ZERO),
                new Pit(CommonMethods.Numbers.ZERO),
                new Pit(CommonMethods.Numbers.ZERO),
                new Pit(CommonMethods.Numbers.ZERO),
                new Pit(CommonMethods.Numbers.ZERO),
                new Pit(CommonMethods.Numbers.ZERO)};
        Pit[] outPits2 = {new Pit(CommonMethods.Numbers.ZERO),
                new Pit(CommonMethods.Numbers.ZERO),
                new Pit(CommonMethods.Numbers.ZERO),
                new Pit(CommonMethods.Numbers.ZERO),
                new Pit(CommonMethods.Numbers.ZERO),
                new Pit(CommonMethods.Numbers.ZERO)};

        Board board = CommonMethods.makeBoard(gameInit,
                outPits1, 40,
                outPits2, 32, 1);
        board.setWinner(1);

        String outputExpectedMockBoard = CommonMethods.mapToJson(board);


        RequestBuilder req = post(MOVE_URI, CommonMethods.Numbers.SIX)
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
