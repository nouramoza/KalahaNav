package com.game.kalaha.web.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
public class Pit {
    private int numOfStones;

    public Pit() {
    }

    public Pit(int numOfStones) {
        this.numOfStones = numOfStones;
    }

    public int getNumOfStones() {
        return numOfStones;
    }

    public void setNumOfStones(int numOfStones) {
        this.numOfStones = numOfStones;
    }
}
