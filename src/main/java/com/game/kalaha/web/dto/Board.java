package com.game.kalaha.web.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
public class Board {
    private GameInit gameInit;
    private Map<Long, Player> playerMap;
    private Map<Long, PlayerArea> playerAreaMap;
    private int winner;

    public Board() {
    }

    public Board(Map<Long, Player> playerMap, Map<Long, PlayerArea> playerAreaMap, GameInit gameInit) {
        this.playerMap = playerMap;
        this.playerAreaMap = playerAreaMap;
        this.gameInit = gameInit;
    }


    public GameInit getGameInit() {
        return gameInit;
    }

    public void setGameInit(GameInit gameInit) {
        this.gameInit = gameInit;
    }

    public Map<Long, Player> getPlayerMap() {
        return playerMap;
    }

    public void setPlayerMap(Map<Long, Player> playerMap) {
        this.playerMap = playerMap;
    }

    public Map<Long, PlayerArea> getPlayerAreaMap() {
        return playerAreaMap;
    }

    public void setPlayerAreaMap(Map<Long, PlayerArea> playerAreaMap) {
        this.playerAreaMap = playerAreaMap;
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }
}
