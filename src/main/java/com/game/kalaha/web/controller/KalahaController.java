package com.game.kalaha.web.controller;

import com.game.kalaha.service.KalahaService;
import com.game.kalaha.web.dto.*;
import com.game.kalaha.web.error.BadRequestAlertException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/kalaha")
@Api(value = "Kalaha game API. Set of endpoints for Creating and Sowing the Game")
public class KalahaController {

    Logger log = LoggerFactory.getLogger(KalahaController.class);
    private KalahaService kalahaService;

    public KalahaController(KalahaService kalahaService) {
        this.kalahaService = kalahaService;
    }

    @PostMapping("/start")
    @ApiOperation(value = "Initialize the board to start the Game",
            produces = "Application/JSON", response = Board.class, httpMethod = "POST")
    public Board start(
            @ApiParam(value = "Initial Config of Board", required = true)
            @RequestBody GameInit gameInit) throws BadRequestAlertException {
        log.debug("REST request to Initialize Board");
        return kalahaService.start(gameInit);
    }

    @PostMapping("/move")
    @ApiOperation(value = "Move from a pit, and returns updated state of the game",
            produces = "Application/JSON", response = Board.class, httpMethod = "POST")
    public Board move(
            @ApiParam(value = "current state of the game", required = true)
            @RequestBody MoveInput moveInput) throws BadRequestAlertException {
        log.debug("REST request to Move from a pit");
        return kalahaService.move(moveInput);
    }

    //Adding and 2 api for"/newgame"

    @PostMapping("/gameInitReturn/{noOfPits}/{noOfStones}/{player1}/{player2}")
    public GameInit gameInitReturn(@PathVariable String player1,
                                   @PathVariable String player2,
                                   @PathVariable int noOfPits,
                                   @PathVariable int noOfStones) {
        String[] names = {player1, player2};
        return new GameInit(2, noOfPits, noOfStones, names, 1);

    }

    @PostMapping("/gameInitReturn/{noOfPits}/{noOfStones}")
    public GameInit gameInitReturn(
                                   @PathVariable int noOfPits,
                                   @PathVariable int noOfStones) {
        return new GameInit(noOfPits, noOfStones);

    }

    @PostMapping("/moveInputReturn/{selectedPitNo}")
    public MoveInput moveInputReturn(@RequestBody Board board,
                                   @PathVariable int selectedPitNo) {

        return new MoveInput(board,selectedPitNo);

    }


}

