package com.game.kalaha.web.dto;

import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Board {
    private GameInit gameInit;
    private Map<Long, Player> playerMap;
    private Map<Long, PlayerArea> playerAreaMap;
    private int winner;
}
