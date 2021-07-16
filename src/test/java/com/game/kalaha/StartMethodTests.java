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
        System.out.println("inputMockGameInit==> " + inputMockGameInit);

        String outputExpectedMockBoard = makeBoard(gameInit);
        System.out.println("outputExpectedMockBoard==> " + outputExpectedMockBoard);
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
        System.out.println("inputMockGameInit==> " + inputMockGameInit);

        String outputExpectedMockBoard = makeBoard(gameInit);
        System.out.println("outputExpectedMockBoard==> " + outputExpectedMockBoard);
        RequestBuilder req = post(START_URI).contentType(MediaType.APPLICATION_JSON)
                .content(inputMockGameInit);


        MvcResult mvcResult = mockMvc.perform(req)
                .andExpect(content().string(containsString(outputExpectedMockBoard)))
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }


    private String makeBoard(GameInit gameInit) throws JsonProcessingException {
        Board board = new Board();
        board.setGameInit(gameInit);

        Player player1 = new Player(CommonMethods.DefaultValues.PLAYER_1, true);
        Player player2 = new Player(CommonMethods.DefaultValues.PLAYER_2, false);
        Map<Long, Player> playerMap = new HashMap<>();
        playerMap.put(1L, player1);
        playerMap.put(2L, player2);
        board.setPlayerMap(playerMap);

        PlayerArea playerArea1 = generatePlayerArea(gameInit);
        PlayerArea playerArea2 = generatePlayerArea(gameInit);
        Map<Long, PlayerArea> playerAreaMap = new HashMap<>();
        playerAreaMap.put(1L, playerArea1);
        playerAreaMap.put(2L, playerArea2);
        board.setPlayerAreaMap(playerAreaMap);
        return CommonMethods.mapToJson(board);

    }

    private PlayerArea generatePlayerArea(GameInit gameInit) {
        Pit[] pits = new Pit[gameInit.getPitPerPlayer()];
        for (int i = 0; i < gameInit.getPitPerPlayer(); i++) {
            Pit pit = new Pit(gameInit.getPitPerPlayer());
            pits[i] = pit;
        }
        Pit bowl = new Pit(0);
        PlayerArea playerArea = new PlayerArea(pits, bowl);
        return playerArea;
    }
}
