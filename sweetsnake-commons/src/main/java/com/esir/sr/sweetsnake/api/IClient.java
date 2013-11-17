package com.esir.sr.sweetsnake.api;

import java.util.List;

import com.esir.sr.sweetsnake.dto.GameRequestDTO;
import com.esir.sr.sweetsnake.dto.GameSessionDTO;
import com.esir.sr.sweetsnake.dto.PlayerDTO;
import com.esir.sr.sweetsnake.enumeration.MoveDirection;
import com.esir.sr.sweetsnake.exception.UnableToConnectException;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public interface IClient
{

    /**********************************************************************************************
     * [BLOCK] GUI EXPOSED METHODS
     **********************************************************************************************/

    /**
     * from the gui
     * 
     */
    void reachServer();

    /**
     * from the gui
     * 
     */
    void connect(String username) throws UnableToConnectException;

    /**
     * from the gui
     * 
     */
    void disconnect();

    /**
     * from the gui
     * 
     * @param player
     */
    void sendRequest(PlayerDTO player);

    /**
     * 
     */
    void readyToPlay();

    /**
     * from the gui
     * 
     */
    void startSession();

    /**
     * from the gui
     * 
     */
    void leaveSession();

    /**
     * from the gui
     * 
     * @param direction
     */
    void moveSnake(MoveDirection direction);

    /**********************************************************************************************
     * [BLOCK] SERVER EXPOSED METHODS
     **********************************************************************************************/

    /**
     * from the server
     * 
     * @param playersList
     */
    void refreshPlayersList(List<PlayerDTO> playersList);

    /**
     * from the server
     * 
     * @param request
     */
    void requestSent(GameRequestDTO request);

    /**
     * from the server
     * 
     * @param request
     */
    void requestReceived(GameRequestDTO request);

    /**
     * from the server
     * 
     * @param allDenied
     * @param request
     */
    void requestDenied(boolean allDenied, GameRequestDTO request);

    /**
     * from the server
     * 
     * @param playerNb
     * @param session
     */
    void sessionJoined(int playerNb, GameSessionDTO session);

    /**
     * from the server
     * 
     * @param playerNb
     * @param session
     */
    void sessionStarted(int playerNb, GameSessionDTO session);

    /**
     * from the server
     * 
     * @param session
     * @param leaver
     * @param finished
     */
    void sessionLeft(GameSessionDTO session, PlayerDTO leaver, boolean finished);

    /**
     * 
     * @param session
     */
    void sessionFinished(GameSessionDTO session);

    /**
     * from the server
     * 
     * @param session
     */
    void refreshSession(GameSessionDTO session);

    /**********************************************************************************************
     * [BLOCK] CLIENT EXPOSED METHODS
     **********************************************************************************************/

    /**
     * 
     * @return
     */
    String getUsername();

}
