package com.game.kalaha.service;

import com.game.kalaha.web.dto.Board;
import com.game.kalaha.web.dto.GameInit;
import com.game.kalaha.web.error.BadRequestAlertException;
import org.springframework.stereotype.Service;

@Service
public interface KalahaService {

    public Board start(GameInit gameInit);

    public Board move(Board board, int pitNo) throws BadRequestAlertException;
}
