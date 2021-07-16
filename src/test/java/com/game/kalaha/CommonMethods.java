package com.game.kalaha;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

}