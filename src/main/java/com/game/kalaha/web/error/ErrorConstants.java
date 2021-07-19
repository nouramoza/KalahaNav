package com.game.kalaha.web.error;

import java.net.URI;

public final class ErrorConstants {

    public static final String ERR_CONCURRENCY_FAILURE = "error.concurrencyFailure";
    public static final String ERR_VALIDATION = "error.validation";
    public static final String PROBLEM_BASE_URL = "https://www.kalahagame.com/problem";
    public static final URI DEFAULT_TYPE = URI.create(PROBLEM_BASE_URL + "/problem-with-message");
    public static final URI CONSTRAINT_VIOLATION_TYPE = URI.create(PROBLEM_BASE_URL + "/constraint-violation");
    public static final URI ENTITY_NOT_FOUND_TYPE = URI.create(PROBLEM_BASE_URL + "/entity-not-found");

    private ErrorConstants() {
    }

    public static class StartExceptionMessages {
        public static final String WRONG_NO_OF_PLAYERS_KEY = "wrongNoOfPlayers";
        public static final String WRONG_NO_OF_PLAYERS_MSG = "Currently More Than two Players is Not available.";
        public static final String WRONG_NO_OF_PITS_KEY = "wrongNoOfPit";
        public static final String WRONG_NO_OF_PITS_MSG = "Pits Number Is Not Valid";
        public static final String WRONG_NO_OF_STONES_KEY = "wrongNoOfStones";
        public static final String WRONG_NO_OF_STONES_MSG = "Stones Number Is Not Valid";
        public static final String WRONG_STARTER_SELECTED_KEY = "wrongPlayerStarterSelected";
        public static final String WRONG_STARTER_SELECTED_MSG = "Starter Player Should Not Be Grater Than Number Of Players.";
    }

    public static class MoveExceptionMessages {
        public static final String WRONG_AREA_SELECTED_KEY = "wrongAreaSelected";
        public static final String WRONG_AREA_SELECTED_MSG = "Wrong Area Is Selected";
        public static final String EMPTY_PIT_SELECTED_KEY = "emptyPitSelected";
        public static final String EMPTY_PIT_SELECTED_MSG = "Wrong Area Is Selected";
    }
}
