package com.game.kalaha.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Player {
    @JsonProperty
    public static int turn;

    private String name;
    private Boolean isTurn;
}
