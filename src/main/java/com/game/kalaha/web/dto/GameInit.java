package com.game.kalaha.web.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class GameInit {
    @NonNull
    private int pitPerPlayer;
    @NonNull
    private int stonePerPit;
    private Long starterPlayerNo = 1L;
}
