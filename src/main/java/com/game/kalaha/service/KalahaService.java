package com.game.kalaha.service;

import com.game.kalaha.web.dto.Board;
import com.game.kalaha.web.dto.GameInit;
import com.game.kalaha.web.dto.MoveInput;
import com.game.kalaha.web.error.BadRequestAlertException;
import org.springframework.stereotype.Service;

@Service
public interface KalahaService {

    public Board start(GameInit gameInit) throws BadRequestAlertException;

    public Board move(MoveInput moveInput) throws BadRequestAlertException;
}
