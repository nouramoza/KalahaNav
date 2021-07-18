package com.game.kalaha.web.dto;

import com.game.kalaha.util.ConstantsUtil;
import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Board {
    @NonNull
    private GameInit gameInit;
    @NonNull
    private Map<Long, Player> playerMap;
    @NonNull
    private Map<Long, PlayerArea> playerAreaMap;
    private int capturedPitNo = ConstantsUtil.DefaultValues.NOT_CAPTURED_PIT;
    private Boolean landsInOwnBowl = false;
    private int winner;
}
