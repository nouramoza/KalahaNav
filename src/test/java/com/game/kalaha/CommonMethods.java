package com.game.kalaha;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.kalaha.web.dto.*;

import java.util.HashMap;
import java.util.Map;

public class CommonMethods {

    public static class DefaultValues {
        public static final int DEFAULT_NO_OF_PLAYERS = 2;
        public static final int DEFAULT_NO_OF_PITS = 6;
        public static final int DEFAULT_NO_OF_STONES = 6;
        public static final String PLAYER_1 = "Player1";
        public static final String PLAYER_2 = "Player2";
    }

    public static class Numbers {
        public static final int ZERO = 0;
        public static final int ONE = 1;
        public static final int TWO = 2;
        public static final int THREE = 3;
        public static final int FOUR = 4;
        public static final int FIVE = 5;
        public static final int SIX = 6;
        public static final int SEVEN = 7;
        public static final int EIGHT = 8;
        public static final int NINE = 9;
        public static final int TEN = 10;
        public static final int ELEVEN = 11;
        public static final int TWELVE = 12;
        public static final int THIRTEEN = 13;
    }


    protected static String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    protected static String makeMoveInput(GameInit gameInit,
                                           Pit[] player1Pits,
                                           int player1Bowl,
                                           Pit[] player2Pits,
                                           int player2Bowl,
                                           int turn,
                                           int selectedPitNo
    ) throws JsonProcessingException {
        Board board = makeBoard( gameInit,
                player1Pits,
        player1Bowl,
        player2Pits,
        player2Bowl,
        turn);


        return CommonMethods.mapToJson(new MoveInput(board, selectedPitNo));
    }

    protected static Board makeBoard(GameInit gameInit,
                                     Pit[] player1Pits,
                                     int player1Bowl,
                                     Pit[] player2Pits,
                                     int player2Bowl,
                                     int turn
    ) throws JsonProcessingException {
        Board board = new Board();
        board.setGameInit(gameInit);

        Player player1 = new Player(CommonMethods.DefaultValues.PLAYER_1, turn == 1);
        Player player2 = new Player(CommonMethods.DefaultValues.PLAYER_2, turn == 2);
        Map<Long, Player> playerMap = new HashMap<>();
        playerMap.put(1L, player1);
        playerMap.put(2L, player2);
        board.setPlayerMap(playerMap);

        Map<Long, PlayerArea> playerAreaMap = new HashMap<>();
        playerAreaMap.put(1L, new PlayerArea(player1Pits, new Pit(player1Bowl)));

        playerAreaMap.put(2L, new PlayerArea(player2Pits, new Pit(player2Bowl)));
        board.setPlayerAreaMap(playerAreaMap);
        return board;
    }


}
