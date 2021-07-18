package com.game.kalaha.web.dto;

import com.game.kalaha.util.ConstantsUtil;
import lombok.*;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class GameInit {
    private int noOfPlayers = ConstantsUtil.DefaultValues.DEFAULT_NO_OF_PLAYERS;
    @NonNull
    private int pitPerPlayer;
    @NonNull
    private int stonePerPit;
    private ArrayList<String> playerNameList;
    private Long starterPlayerNo = 1L;
}
