package com.game.kalaha.web.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Set;

@Data
//@AllArgsConstructor(access = AccessLevel.PRIVATE)
//@NoArgsConstructor
//@Getter
//@Setter
public class Player implements Serializable {
//    public static int turn;
    private String name;
    private Boolean isTurn;

    public Player() {
    }

    public Player(String name, Boolean isTurn) {
        this.name = name;
        this.isTurn = isTurn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getTurn() {
        return isTurn;
    }

    public void setTurn(Boolean turn) {
        isTurn = turn;
    }
}
