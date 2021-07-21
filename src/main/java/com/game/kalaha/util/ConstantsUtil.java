package com.game.kalaha.util;

//Constant Design pattern
public final class ConstantsUtil {

    private ConstantsUtil() {
    }

    public static class DefaultValues {
        public static final int DEFAULT_NO_OF_PLAYERS = 2;
        public static final int DEFAULT_NO_OF_PITS = 6;
        public static final int DEFAULT_NO_OF_STONES = 6;
        public static final int NO_PIT_CAPTURED = -1;
        public static final String DEFAULT_PLAYER_NAME = "Player";
    }
}
