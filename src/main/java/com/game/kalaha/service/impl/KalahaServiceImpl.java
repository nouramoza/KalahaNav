package com.game.kalaha.service.impl;

import com.game.kalaha.service.KalahaService;
import com.game.kalaha.util.ConstantsUtil;
import com.game.kalaha.web.dto.*;
import com.game.kalaha.web.error.BadRequestAlertException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class KalahaServiceImpl implements KalahaService {

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
        Board board = new Board(playerMap, playerAreaMap, gameInit);
        return board;
    }

    private PlayerArea generatePlayerArea(GameInit gameInit) {
        Pit[] pits = new Pit[gameInit.getPitPerPlayer()];
        for (int i = 0; i < gameInit.getPitPerPlayer(); i++) {
            Pit pit = new Pit(gameInit.getPitPerPlayer());
            pits[i] = pit;
        }
        Pit bowl = new Pit(0);
        PlayerArea playerArea = new PlayerArea(pits, bowl);
        return playerArea;
    }

    @Override
    public Board move(Board board, int selectedPitNo) throws BadRequestAlertException {

        int mainTurn = 0;
//        Player player = board.getPlayerMap().get(Long.valueOf(turn));
        for (int i = 1; i < 3; i++) {
            if (board.getPlayerMap().get(Long.valueOf(i)).getTurn()) {
                mainTurn = i;
                break;
            }
        }

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

    private void checkOwnPitIsSelected(Board board, int selectedPitNo, int turn) throws BadRequestAlertException {
        int noOfPitsPerPit = board.getGameInit().getPitPerPlayer();
//        int turn = Player.turn;
        if (!(selectedPitNo > ((noOfPitsPerPit) - noOfPitsPerPit) && selectedPitNo <= (noOfPitsPerPit))) {
            throw new BadRequestAlertException();
        }
    }

    private void checkPitIsEmpty(Board board, int selectedPitNo, int turn) throws BadRequestAlertException {
        if (board.getPlayerAreaMap().get(Long.valueOf(turn)).getPits()[selectedPitNo - 1].getNumOfStones() == 0) {
            throw new BadRequestAlertException();
        }
    }

    private int doMove(Board board, int selectedPitNo, int mainTurn) {
        int capturedPit = -1;
        int playerAreaNo = mainTurn;

        //find number of stones of the selected pit
        int selectedPitTotalStones = board.getPlayerAreaMap().get(Long.valueOf(mainTurn)).getPits()[selectedPitNo - 1].getNumOfStones();

        board.getPlayerAreaMap().get(Long.valueOf(playerAreaNo)).getPits()[selectedPitNo -1].setNumOfStones(0);

        //find start point of the game set
        int startPoint = selectedPitNo;
        if (selectedPitNo + 1 > board.getGameInit().getPitPerPlayer()) {

            board.getPlayerAreaMap().get(Long.valueOf(playerAreaNo)).getBowl().setNumOfStones(
                    board.getPlayerAreaMap().get(Long.valueOf(playerAreaNo)).getBowl().getNumOfStones() + 1);
            selectedPitTotalStones--;
            if (selectedPitTotalStones == 0) {
                capturedPit = -2;
            }
            startPoint = 0;
            playerAreaNo = findOtherPlayerTurn(playerAreaNo);
        }

        //empty the selected pit
        while (selectedPitTotalStones > 0) {
            int total = Math.min(board.getGameInit().getPitPerPlayer(), startPoint + selectedPitTotalStones);
            for (int i = startPoint; i < total; i++) {
                board.getPlayerAreaMap().get(Long.valueOf(playerAreaNo)).getPits()[i].setNumOfStones(
                        board.getPlayerAreaMap().get(Long.valueOf(playerAreaNo)).getPits()[i].getNumOfStones() + 1);
                selectedPitTotalStones--;
                if (selectedPitTotalStones == 0) {
                    if (mainTurn == playerAreaNo) {
                        capturedPit = i;
                    }
                    break;
                }
            }

            //check Lands in main player bowl
            if (mainTurn == playerAreaNo && capturedPit < 0) {
                board.getPlayerAreaMap().get(Long.valueOf(playerAreaNo)).getBowl().setNumOfStones(
                        board.getPlayerAreaMap().get(Long.valueOf(playerAreaNo)).getBowl().getNumOfStones() + 1);
                selectedPitTotalStones--;
                if (selectedPitTotalStones == 0) {
                    capturedPit = -2;
                }
            }
            playerAreaNo = findOtherPlayerTurn(playerAreaNo);
            startPoint = 0;
        }

        return capturedPit;

    }

    private void updateTurn(Board board, int turn) {
        board.getPlayerMap().get(Long.valueOf(turn)).setTurn(false);
        board.getPlayerMap().get(Long.valueOf(findOtherPlayerTurn(turn))).setTurn(true);
    }

//    private void checkIsCapture() {
//
//    }

    private void doCapture(Board board, int mainCapturedPitNo, int mainTurn) {
        if (mainCapturedPitNo > 0) {
            int capturedTurn = findOtherPlayerTurn(mainTurn);
            int capturedPitNo = board.getGameInit().getPitPerPlayer() - 1 - mainCapturedPitNo;
            int noOfStonesInCapturedPit = board.getPlayerAreaMap().get(Long.valueOf(capturedTurn))
                    .getPits()[capturedPitNo].getNumOfStones() + 1;
            if (noOfStonesInCapturedPit > 1) {
                board.getPlayerAreaMap().get(Long.valueOf(mainTurn))
                        .getPits()[mainCapturedPitNo].setNumOfStones(0);

                board.getPlayerAreaMap().get(Long.valueOf(capturedTurn))
                        .getPits()[capturedPitNo].setNumOfStones(0);

                board.getPlayerAreaMap().get(Long.valueOf(mainTurn))
                        .getBowl().setNumOfStones(board.getPlayerAreaMap().get(Long.valueOf(mainTurn))
                        .getBowl().getNumOfStones() + noOfStonesInCapturedPit);

            }

        }
    }

//    private void checkLandsInBigPit() {
//
//    }

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

    private void checkGameIsOver() {

    }

    private int findOtherPlayerTurn(int turn) {
        return turn % 2 + 1;
    }
}
