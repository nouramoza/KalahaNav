package com.game.kalaha.web.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
//@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class PlayerArea {
    private Pit[] pits;
    private Pit bowl;

    public PlayerArea() {
    }

    public PlayerArea(Pit[] pits, Pit bowl) {
        this.pits = pits;
        this.bowl = bowl;
    }

    public Pit[] getPits() {
        return pits;
    }

    public void setPits(Pit[] pits) {
        this.pits = pits;
    }

    public Pit getBowl() {
        return bowl;
    }

    public void setBowl(Pit bowl) {
        this.bowl = bowl;
    }
}
