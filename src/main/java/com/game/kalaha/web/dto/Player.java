package com.game.kalaha.web.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Player {
    private String name;
    private Boolean isTurn;
    @NonNull
    private PlayerArea playerArea;
}
