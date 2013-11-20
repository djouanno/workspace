package com.esir.sr.sweetsnake.api;

import java.util.List;
import java.util.Map;

import com.esir.sr.sweetsnake.dto.GameBoardDTO;
import com.esir.sr.sweetsnake.dto.GameRequestDTO;
import com.esir.sr.sweetsnake.dto.GameSessionDTO;
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
public interface IGuiForClient
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
     */
    void disconnectedFromServer();

    /**
     * 
     * @param playersList
     */
    void refreshPlayersList(List<PlayerDTO> playersList);

    /**
     * 
     * @param sessionsList
     */
    void refreshSessionsList(List<GameSessionDTO> sessionsList);

    /**
     * 
     * @param request
     */
    void requestSent(GameRequestDTO request);

    /**
     * 
     * @param request
     * @return
     */
    int requestReceived(GameRequestDTO request);

    /**
     * 
     * @param request
     */
    void requestDenied(GameRequestDTO request);

    /**
     * 
     * @param playerNb
     * @param players
     */
    void sessionJoined(int playerNb, List<PlayerDTO> players);

    /**
     * 
     * @param playerNb
     * @param playersSnakes
     * @param gameBoard
     */
    void sessionStarted(int playerNb, Map<Integer, String> playersSnakes, GameBoardDTO gameBoard);

    /**
     * 
     * @param players
     * @param leaver
     * @param stopped
     * @param finished
     */
    void sessionLeft(List<PlayerDTO> players, PlayerDTO leaver, boolean stopped, boolean finished);

    /**
     * 
     * @param playersScores
     */
    void sessionFinished(List<PlayerDTO> players);

    /**
     * 
     * @param gameBoard
     */
    void refreshGameboard(GameBoardDTO gameBoard);

    /**
     * 
     * @param players
     */
    void refreshScores(List<PlayerDTO> players);

    /**
     * 
     * @param message
     */
    void displayErrorMessage(String message);

}
