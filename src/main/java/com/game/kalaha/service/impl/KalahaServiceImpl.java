package com.game.kalaha.service.impl;

import com.game.kalaha.service.KalahaService;
import com.game.kalaha.util.ConstantsUtil;
import com.game.kalaha.web.dto.*;
import com.game.kalaha.web.error.BadRequestAlertException;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.game.kalaha.web.error.ErrorConstants.MoveExceptionMessages.*;
import static com.game.kalaha.web.error.ErrorConstants.StartExceptionMessages.*;

/**
 * Service Implementation for managing Game.
 */

@Service
public class KalahaServiceImpl implements KalahaService {

    private static final String PLAYER_AREA_MODEL = "PlayerArea";
    private static final String PIT_MODEL = "Pit";

    /**
     * Initialize Board with initial data
     *
     * @param gameInit Initial Config of Board
     * @return Board Initial Board
     */
    @Override
    public Board start(GameInit gameInit) throws BadRequestAlertException {
        checkInputValidation(gameInit);
        setInputNullVariables(gameInit);
        return generateBoard(gameInit);
    }

    private void checkInputValidation(GameInit gameInit) throws BadRequestAlertException {
        if (gameInit.getNoOfPlayers() > ConstantsUtil.DefaultValues.DEFAULT_NO_OF_PLAYERS ||
                gameInit.getNoOfPlayers() < ConstantsUtil.DefaultValues.DEFAULT_NO_OF_PLAYERS) {
            throw new BadRequestAlertException(WRONG_NO_OF_PLAYERS_MSG, PLAYER_AREA_MODEL, WRONG_NO_OF_PLAYERS_KEY);
        }
        if (gameInit.getPitPerPlayer() <  1) {
            throw new BadRequestAlertException(WRONG_NO_OF_PITS_MSG, PLAYER_AREA_MODEL, WRONG_NO_OF_PITS_KEY);
        }
        if (gameInit.getStonePerPit() <  1) {
            throw new BadRequestAlertException(WRONG_NO_OF_STONES_MSG, PLAYER_AREA_MODEL, WRONG_NO_OF_STONES_KEY);
        }
        if (gameInit.getStarterPlayerNo() > gameInit.getNoOfPlayers() || gameInit.getStarterPlayerNo() < 1) {
            throw new BadRequestAlertException(WRONG_STARTER_SELECTED_MSG, PLAYER_AREA_MODEL, WRONG_STARTER_SELECTED_KEY);
        }
    }

    private void setInputNullVariables(GameInit gameInit) {
        if (gameInit.getPitPerPlayer() == 0) {
            gameInit.setPitPerPlayer(ConstantsUtil.DefaultValues.DEFAULT_NO_OF_PITS);
        }

        if (gameInit.getStonePerPit() == 0) {
            gameInit.setStonePerPit(ConstantsUtil.DefaultValues.DEFAULT_NO_OF_STONES);
        }

    }

    private Board generateBoard(GameInit gameInit) {
        Map<Long, Player> playerMap = new HashMap<>();
        Long index = 1L;
        PlayerArea playerArea = generatePlayerArea(gameInit);
        playerMap.put(index, generatePlayer(gameInit, playerArea, index++));
        playerMap.put(index, generatePlayer(gameInit, playerArea, index));
        return new Board(gameInit, playerMap);
    }

    private Player generatePlayer(GameInit gameInit, PlayerArea playerArea, Long index) {
        return new Player(
                gameInit.getPlayerNameList() != null ?
                        gameInit.getPlayerNameList()[Math.toIntExact(index -1)] :
                        ConstantsUtil.DefaultValues.DEFAULT_PLAYER_NAME.concat(String.valueOf(index)),
                gameInit.getStarterPlayerNo() == index.intValue(), playerArea);
    }

    private PlayerArea generatePlayerArea(GameInit gameInit) {
        Pit[] pits = new Pit[gameInit.getPitPerPlayer()];
        Pit pit = new Pit(gameInit.getStonePerPit());
        for (int i = 0; i < gameInit.getPitPerPlayer(); i++) {
            pits[i] = pit;
        }
        Pit bowl = new Pit(0);
        return new PlayerArea(pits, bowl);
    }

    /**
     * Do move from a pit
     *
     * @param moveInput current state of the game consists of board and selected pit number
     * @return Board updated state of the game
     */
    @Override
    public Board move(MoveInput moveInput) throws BadRequestAlertException {
        Board board = moveInput.getBoard();
        int selectedPitNo = moveInput.getSelectedPitNo();

        int roundTurn = findTurn(board);
        checkInputValidity(board, selectedPitNo, roundTurn);
        setInitialValues(board);
        doMove(board, selectedPitNo, roundTurn);
        doCapture(board, roundTurn);
        checkAndDoFinalizeGame(board, roundTurn);
        checkAndDoFinalizeGame(board, findOtherPlayerTurn(roundTurn, board.getGameInit().getNoOfPlayers()));
        updateTurn(board, roundTurn);
        return board;
    }

    private int findTurn(Board board) {
        int roundTurn = 0;
        for (int i = 1; i < 3; i++) {
            if (board.getPlayerMap().get(Long.valueOf(i)).getIsTurn()) {
                roundTurn = i;
                break;
            }
        }
        return roundTurn;

    }

    private void checkInputValidity(Board board, int selectedPitNo, int roundTurn) throws BadRequestAlertException {
        int noOfPitsPerPit = board.getGameInit().getPitPerPlayer();
        if (!(selectedPitNo > ((noOfPitsPerPit) - noOfPitsPerPit) && selectedPitNo <= (noOfPitsPerPit))) {
            throw new BadRequestAlertException(WRONG_AREA_SELECTED_MSG, PLAYER_AREA_MODEL, WRONG_AREA_SELECTED_KEY);
        }
        if (board.getPlayerMap().get(Long.valueOf(roundTurn)).getPlayerArea().getPits()[selectedPitNo - 1].getNumOfStones() == 0) {
            throw new BadRequestAlertException(EMPTY_PIT_SELECTED_MSG, PIT_MODEL, EMPTY_PIT_SELECTED_KEY);
        }
    }

    private void setInitialValues(Board board) {
        board.setLandsInOwnBowl(false);
        board.setCapturedPitNo(ConstantsUtil.DefaultValues.NO_PIT_CAPTURED);
    }

    private void doMove(Board board, int selectedPitNo, int roundTurn) {
        int playerAreaNo = roundTurn;

        /** find number of stones of the selected pit*/
        int selectedPitTotalStones = board.getPlayerMap().get(Long.valueOf(roundTurn)).getPlayerArea().getPits()[selectedPitNo - 1].getNumOfStones();

        /**empty the selected pit*/
        board.getPlayerMap().get(Long.valueOf(roundTurn)).getPlayerArea().getPits()[selectedPitNo -1].setNumOfStones(0);

        /**find start point of the game set*/
        int startPoint = selectedPitNo;

        /** if starts from bowl*/
        if (selectedPitNo + 1 > board.getGameInit().getPitPerPlayer()) {
            /** add one stone to bowl and starts from next player's first pit*/
            board.getPlayerMap().get(Long.valueOf(playerAreaNo)).getPlayerArea().getBowl().setNumOfStones(
                    board.getPlayerMap().get(Long.valueOf(playerAreaNo)).getPlayerArea().getBowl().getNumOfStones() + 1);
            selectedPitTotalStones--;
            /** check if Lands in main player bowl*/
            board.setLandsInOwnBowl(selectedPitTotalStones == 0);
            startPoint = 0;
            playerAreaNo = findOtherPlayerTurn(playerAreaNo, board.getGameInit().getNoOfPlayers());
        }

        while (selectedPitTotalStones > 0) {
            /** find no. of remained pits of current playerArea*/
            int total = Math.min(board.getGameInit().getPitPerPlayer(), startPoint + selectedPitTotalStones);
            for (int i = startPoint; i < total; i++) {

                board.getPlayerMap().get(Long.valueOf(playerAreaNo)).getPlayerArea().getPits()[i].setNumOfStones(
                        board.getPlayerMap().get(Long.valueOf(playerAreaNo)).getPlayerArea().getPits()[i].getNumOfStones() + 1);
                selectedPitTotalStones--;

                /** check if captured*/
                if (selectedPitTotalStones == 0) {
                    if (roundTurn == playerAreaNo &&
                            board.getPlayerMap().get(Long.valueOf(playerAreaNo)).getPlayerArea().getPits()[i].getNumOfStones() == 1) {
                        board.setCapturedPitNo(i);
                    }
                    break;
                }
            }

            /** check if Lands in main player bowl*/
            if (selectedPitTotalStones > 0 && roundTurn == playerAreaNo && board.getCapturedPitNo() < 0) {
                board.getPlayerMap().get(Long.valueOf(playerAreaNo)).getPlayerArea().getBowl().setNumOfStones(
                        board.getPlayerMap().get(Long.valueOf(playerAreaNo)).getPlayerArea().getBowl().getNumOfStones() + 1);
                selectedPitTotalStones--;
                board.setLandsInOwnBowl(selectedPitTotalStones == 0);
            }
            /* find next playerArea*/
            playerAreaNo = findOtherPlayerTurn(playerAreaNo, board.getGameInit().getNoOfPlayers());
            /** next loop starts from first pit*/
            startPoint = 0;
        }
    }

    private void updateTurn(Board board, int turn) {
        if (!board.getLandsInOwnBowl()) {
            board.getPlayerMap().get(Long.valueOf(turn)).setIsTurn(false);
            board.getPlayerMap().get(Long.valueOf(findOtherPlayerTurn(turn, board.getGameInit().getNoOfPlayers()))).setIsTurn(true);
        }
    }

    private void doCapture(Board board, int roundTurn) {
        int mainCapturedPitNo = board.getCapturedPitNo();
        if (mainCapturedPitNo > 0) {
            int capturedTurn = findOtherPlayerTurn(roundTurn, board.getGameInit().getNoOfPlayers());
            int capturedPitNo = board.getGameInit().getPitPerPlayer() - 1 - mainCapturedPitNo;
            int noOfStonesInCapturedPit = board.getPlayerMap().get(Long.valueOf(capturedTurn))
                    .getPlayerArea().getPits()[capturedPitNo].getNumOfStones();
            if (noOfStonesInCapturedPit > 0) {
                /** emptied the current player's pit*/
                board.getPlayerMap().get(Long.valueOf(roundTurn))
                        .getPlayerArea().getPits()[mainCapturedPitNo].setNumOfStones(0);

                /** emptied the other player's captured pit*/
                board.getPlayerMap().get(Long.valueOf(capturedTurn))
                        .getPlayerArea().getPits()[capturedPitNo].setNumOfStones(0);

                /** add the stones to current players bowl*/
                board.getPlayerMap().get(Long.valueOf(roundTurn)).getPlayerArea()
                        .getBowl().setNumOfStones(board.getPlayerMap().get(Long.valueOf(roundTurn))
                        .getPlayerArea().getBowl().getNumOfStones() + noOfStonesInCapturedPit + 1);
            }
        }
    }

    private boolean checkAllPitsEmpty(Board board, int roundTurn) {
        boolean gameOver = true;
        for (int j = 0; j < board.getGameInit().getPitPerPlayer(); j++) {
            if (board.getPlayerMap().get(Long.valueOf(roundTurn)).getPlayerArea().getPits()[j].getNumOfStones() != 0) {
                gameOver = false;
                break;
            }
        }
        return gameOver;
    }

    private void checkAndDoFinalizeGame(Board board, int roundTurn) {
        if (board.getWinner() == 0 && checkAllPitsEmpty(board, roundTurn)) {
            int totalRemainedStones = 0;
            int otherPlayerTurn = findOtherPlayerTurn(roundTurn, board.getGameInit().getNoOfPlayers());

            for (int i = 0; i < board.getGameInit().getPitPerPlayer(); i++) {
                totalRemainedStones += board.getPlayerMap().get(Long.valueOf(otherPlayerTurn)).getPlayerArea().getPits()[i].getNumOfStones();
                board.getPlayerMap().get(Long.valueOf(otherPlayerTurn)).getPlayerArea().getPits()[i].setNumOfStones(0);
            }

            board.getPlayerMap().get(Long.valueOf(roundTurn)).getPlayerArea().getBowl().setNumOfStones(
                    board.getPlayerMap().get(Long.valueOf(roundTurn)).getPlayerArea().getBowl().getNumOfStones() + totalRemainedStones
            );
            board.setWinner(roundTurn);
        }
    }

    private int findOtherPlayerTurn(int turn, int noOfPlayers) {
        return turn % noOfPlayers + 1;
    }
}