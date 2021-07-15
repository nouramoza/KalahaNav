package com.game.kalaha.web.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
//@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class GameInit {
    private int pitPerPlayer;
    private int stonePerPit;

    public GameInit() {
    }

    public GameInit(int pitPerPlayer, int stonePerPit) {
        this.pitPerPlayer = pitPerPlayer;
        this.stonePerPit = stonePerPit;
    }

    public int getStonePerPit() {
        return stonePerPit;
    }

    public void setStonePerPit(int stonePerPit) {
        this.stonePerPit = stonePerPit;
    }

    public int getPitPerPlayer() {
        return pitPerPlayer;
    }

    public void setPitPerPlayer(int pitPerPlayer) {
        this.pitPerPlayer = pitPerPlayer;
    }
}
