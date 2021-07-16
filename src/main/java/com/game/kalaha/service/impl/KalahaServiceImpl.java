package com.game.kalaha.service.impl;

import com.game.kalaha.service.KalahaService;
import com.game.kalaha.web.dto.*;
import com.game.kalaha.web.error.BadRequestAlertException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service Implementation for managing Game.
 */

@Service
public class KalahaServiceImpl implements KalahaService {

    private static final String MODEL_NAME_1 = "PlayerArea";
    private static final String MODEL_NAME_2 = "Pit";

    /**
     * Initialize Board with initial data
     *
     * @param gameInit Initial Config of Board
     * @return Board Initial Board
     */
    @Override
    public Board start(GameInit gameInit) {

        Map<Long, Player> playerMap = new HashMap<>();
        Player player1 = new Player("Player1", true);
        playerMap.put(1L, player1);
        Player player2 = new Player("Player2", false);
        playerMap.put(2L, player2);
        Map<Long, PlayerArea> playerAreaMap = new HashMap<>();
        playerAreaMap.put(1L, generatePlayerArea(gameInit));
        playerAreaMap.put(2L, generatePlayerArea(gameInit));
        Board board = new Board(gameInit, playerMap, playerAreaMap, 0);
        return board;
    }

    private PlayerArea generatePlayerArea(GameInit gameInit) {
        Pit[] pits = new Pit[gameInit.getPitPerPlayer()];
        Pit pit = new Pit(gameInit.getStonePerPit());
        for (int i = 0; i < gameInit.getPitPerPlayer(); i++) {
            pits[i] = pit;
        }
        Pit bowl = new Pit(0);
        PlayerArea playerArea = new PlayerArea(pits, bowl);
        return playerArea;
    }

    /**
     * Do move from a pit
     *
     * @param board current state of the game
     * @param selectedPitNo selected pit number
     * @return Board updated state of the game
     */
    @Override
    public Board move(Board board, int selectedPitNo) throws BadRequestAlertException {

        int mainTurn = findTurn(board);
        checkOwnPitIsSelected(board, selectedPitNo, mainTurn);
        checkPitIsEmpty(board, selectedPitNo, mainTurn);
        int capturedPit = doMove(board, selectedPitNo, mainTurn);
        doCapture(board, capturedPit, mainTurn);
        if (checkAllPitsEmpty(board, mainTurn)) {
            doFinalizeGame(board, mainTurn);
        }
        if (capturedPit != -2) {
            updateTurn(board, mainTurn);
        }
        return board;
    }

    private int findTurn(Board board) {
        int mainTurn = 0;
        for (int i = 1; i < 3; i++) {
            if (board.getPlayerMap().get(Long.valueOf(i)).getIsTurn()) {
                mainTurn = i;
                break;
            }
        }
        return mainTurn;

    }

    private void checkOwnPitIsSelected(Board board, int selectedPitNo, int turn) throws BadRequestAlertException {
        int noOfPitsPerPit = board.getGameInit().getPitPerPlayer();
        if (!(selectedPitNo > ((noOfPitsPerPit) - noOfPitsPerPit) && selectedPitNo <= (noOfPitsPerPit))) {
            throw new BadRequestAlertException("Wrong Area Is Selected", MODEL_NAME_1, "wrongAreaSelected");
        }
    }

    private void checkPitIsEmpty(Board board, int selectedPitNo, int turn) throws BadRequestAlertException {
        if (board.getPlayerAreaMap().get(Long.valueOf(turn)).getPits()[selectedPitNo - 1].getNumOfStones() == 0) {
            throw new BadRequestAlertException("The Selected Pit Is Empty", MODEL_NAME_2, "emptyPitSelected");
        }
    }

    private int doMove(Board board, int selectedPitNo, int mainTurn) {
        int capturedPit = -1;
        int playerAreaNo = mainTurn;

        //find number of stones of the selected pit
        int selectedPitTotalStones = board.getPlayerAreaMap().get(Long.valueOf(mainTurn)).getPits()[selectedPitNo - 1].getNumOfStones();

        //empty the selected pit
        board.getPlayerAreaMap().get(Long.valueOf(mainTurn)).getPits()[selectedPitNo -1].setNumOfStones(0);

        //find start point of the game set
        int startPoint = selectedPitNo;

        //if starts from bowl
        if (selectedPitNo + 1 > board.getGameInit().getPitPerPlayer()) {
            //add one stone to bowl and starts from next player's first pit
            board.getPlayerAreaMap().get(Long.valueOf(playerAreaNo)).getBowl().setNumOfStones(
                    board.getPlayerAreaMap().get(Long.valueOf(playerAreaNo)).getBowl().getNumOfStones() + 1);
            selectedPitTotalStones--;
            //check if Lands in main player bowl
            if (selectedPitTotalStones == 0) {
                capturedPit = -2;
            }
            startPoint = 0;
            playerAreaNo = findOtherPlayerTurn(playerAreaNo);
        }

        while (selectedPitTotalStones > 0) {
            //find no. of remained pits of current playerArea
            int total = Math.min(board.getGameInit().getPitPerPlayer(), startPoint + selectedPitTotalStones);
            for (int i = startPoint; i < total; i++) {

                board.getPlayerAreaMap().get(Long.valueOf(playerAreaNo)).getPits()[i].setNumOfStones(
                        board.getPlayerAreaMap().get(Long.valueOf(playerAreaNo)).getPits()[i].getNumOfStones() + 1);
                selectedPitTotalStones--;

                //check if captured
                if (selectedPitTotalStones == 0) {
                    if (mainTurn == playerAreaNo &&
                            board.getPlayerAreaMap().get(Long.valueOf(playerAreaNo)).getPits()[i].getNumOfStones() == 1) {
                        capturedPit = i;
                    }
                    break;
                }
            }

            //check if Lands in main player bowl
            if (selectedPitTotalStones > 0 && mainTurn == playerAreaNo && capturedPit < 0) {
                board.getPlayerAreaMap().get(Long.valueOf(playerAreaNo)).getBowl().setNumOfStones(
                        board.getPlayerAreaMap().get(Long.valueOf(playerAreaNo)).getBowl().getNumOfStones() + 1);
                selectedPitTotalStones--;
                if (selectedPitTotalStones == 0) {
                    capturedPit = -2;
                }
            }
            //find next playerArea
            playerAreaNo = findOtherPlayerTurn(playerAreaNo);
            //next loop starts from first pit
            startPoint = 0;
        }

        return capturedPit;

    }

    private void updateTurn(Board board, int turn) {
        board.getPlayerMap().get(Long.valueOf(turn)).setIsTurn(false);
        board.getPlayerMap().get(Long.valueOf(findOtherPlayerTurn(turn))).setIsTurn(true);
    }

    private void doCapture(Board board, int mainCapturedPitNo, int mainTurn) {
        if (mainCapturedPitNo > 0) {
            int capturedTurn = findOtherPlayerTurn(mainTurn);
            int capturedPitNo = board.getGameInit().getPitPerPlayer() - 1 - mainCapturedPitNo;
            int noOfStonesInCapturedPit = board.getPlayerAreaMap().get(Long.valueOf(capturedTurn))
                    .getPits()[capturedPitNo].getNumOfStones();
            if (noOfStonesInCapturedPit > 0) {
                //emptied the current player's pit
                board.getPlayerAreaMap().get(Long.valueOf(mainTurn))
                        .getPits()[mainCapturedPitNo].setNumOfStones(0);

                //emptied the other player's captured pit
                board.getPlayerAreaMap().get(Long.valueOf(capturedTurn))
                        .getPits()[capturedPitNo].setNumOfStones(0);

                //add the stones to current players bowl
                board.getPlayerAreaMap().get(Long.valueOf(mainTurn))
                        .getBowl().setNumOfStones(board.getPlayerAreaMap().get(Long.valueOf(mainTurn))
                        .getBowl().getNumOfStones() + noOfStonesInCapturedPit + 1);
            }
        }
    }

    private Boolean checkAllPitsEmpty(Board board, int mainTurn) {
        Boolean gameOver = true;
        for (int j = 0; j < board.getGameInit().getPitPerPlayer(); j++) {
            if (board.getPlayerAreaMap().get(Long.valueOf(mainTurn)).getPits()[j].getNumOfStones() != 0) {
                gameOver = false;
                break;
            }
        }
        return gameOver;
    }

    private String doFinalizeGame(Board board, int mainTurn) {
        int totalRemainedStones = 0;
        int otherPlayerTurn = findOtherPlayerTurn(mainTurn);

        for (int i = 0; i < board.getGameInit().getPitPerPlayer(); i++) {
            totalRemainedStones += board.getPlayerAreaMap().get(Long.valueOf(otherPlayerTurn)).getPits()[i].getNumOfStones();
            board.getPlayerAreaMap().get(Long.valueOf(otherPlayerTurn)).getPits()[i].setNumOfStones(0);
        }

        board.getPlayerAreaMap().get(Long.valueOf(mainTurn)).getBowl().setNumOfStones(
                board.getPlayerAreaMap().get(Long.valueOf(mainTurn)).getBowl().getNumOfStones() + totalRemainedStones
        );
        board.setWinner(mainTurn);
        return "Game Over: Player" + mainTurn + "Wins";
    }

    private int findOtherPlayerTurn(int turn) {
        return turn % 2 + 1;
    }
}
