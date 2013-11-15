package com.esir.sr.sweetsnake.api;

import java.util.List;
import java.util.Map;

import com.esir.sr.sweetsnake.dto.GameBoardDTO;
import com.esir.sr.sweetsnake.dto.GameRequestDTO;
import com.esir.sr.sweetsnake.dto.PlayerDTO;

/**
 * 
 * This interface represents which methods a Graphic User Interface for the game must be able to provide.<br />
 * All the methods below are intented to be called by the client according to the events it processed.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public interface IGui
{

    /**
     * 
     */
    void serverReachable();

    /**
     * 
     */
    void serverNotReachable();

    /**
     * 
     */
    void connectedToServer();

    /**
     * 
     * @param playersList
     */
    void refreshPlayersList(List<PlayerDTO> playersList);

    /**
     * 
     * @param request
     * @return
     */
    int gameRequested(GameRequestDTO request);

    /**
     * 
     * @param request
     */
    void requestSent(GameRequestDTO request);

    /**
     * 
     * @param allDenied
     * @param request
     */
    void requestRefused(boolean allDenied, GameRequestDTO request);

    /**
     * 
     * @param players
     */
    void gameJoined(List<PlayerDTO> players);

    /**
     * 
     * @param playerNb
     * @param playersSnakes
     * @param gameBoard
     */
    void gameStarted(int playerNb, Map<Integer, String> playersSnakes, GameBoardDTO gameBoard);

    /**
     * 
     * @param leaver
     * @param finished
     */
    void gameLeft(PlayerDTO leaver, boolean finished);

    /**
     * 
     * @param gameBoard
     */
    void refreshGameboard(GameBoardDTO gameBoard);

    /**
     * 
     * @param playersScores
     */
    void refreshScores(Map<Integer, Integer> playersScores);

    /**
     * 
     * @param message
     */
    void displayErrorMessage(String message);

}
