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
    private int capturedPitNo = ConstantsUtil.DefaultValues.NO_PIT_CAPTURED;
    private Boolean landsInOwnBowl = false;
    private int winner;
}
