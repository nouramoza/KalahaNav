package com.game.kalaha.web.controller;

import com.game.kalaha.service.KalahaService;
import com.game.kalaha.web.dto.*;
import com.game.kalaha.web.error.BadRequestAlertException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
            @RequestBody GameInit gameInit) {
        log.debug("REST request to Initialize Board");
        return kalahaService.start(gameInit);
    }



    @ResponseBody

    @PostMapping("/move/{pitNo}")
    @ApiOperation(value = "Move from a pIT",
            produces = "Application/JSON", response = Board.class, httpMethod = "POST")
    public Board move(
            @ApiParam(value = "Move from a pit No", required = true)
            @RequestBody Board board,
            @ApiParam(value = "Move from a pit No", required = true)
            @PathVariable int pitNo
                      ) throws BadRequestAlertException {
        log.debug("REST request to Move from a pit");
        return kalahaService.move(board, pitNo);
    }

    @PostMapping("/gameInitReturn/{noOfPits}/ {noOfStones}")
    public GameInit gameInitReturn(@PathVariable int noOfPits,
                                   @PathVariable int noOfStones) {

        return new GameInit(noOfPits,noOfStones);

    }


}

