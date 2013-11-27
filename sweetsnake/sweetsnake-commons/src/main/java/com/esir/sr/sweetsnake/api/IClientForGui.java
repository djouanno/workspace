package com.esir.sr.sweetsnake.api;

import com.esir.sr.sweetsnake.dto.GameSessionDTO;
import com.esir.sr.sweetsnake.dto.PlayerDTO;
import com.esir.sr.sweetsnake.enumeration.MoveDirection;
import com.esir.sr.sweetsnake.exception.UnableToConnectException;

/**
 * This interface represents which methods a client must be able to provide to a GUI.<br />
 * All the methods below are intented to be called by the GUI according to the events it processed.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public interface IClientForGui extends IClient
{

    /**********************************************************************************************
     * [BLOCK] GUI EXPOSED METHODS
     **********************************************************************************************/

    /**
     * This method is called by the GUI in order to ask the client to try to reach the server
     */
    void reachServer();

    /**
     * This method is called by the GUI in order to ask the client to try to connect to the server
     * 
     * @param username
     *            The username chosen by the user
     * @throws UnableToConnectException
     *             If the connection was not possible (bad username)
     */
    void connect(String username) throws UnableToConnectException;

    /**
     * This method is called by the GUI in order to ask the client to disconnect from the server
     */
    void disconnect();

    /**
     * This method is called by the GUI in order to ask the client to sent a request to another player
     * 
     * @param player
     *            The DTO representing the other player to invite
     */
    void sendRequest(PlayerDTO player);

    /**
     * This method is called by the GUI in order to ask the client to create a game session on the server
     */
    void createSession();

    /**
     * This method is called by the GUI in order to ask the client to join a running session on the server
     * 
     * @param session
     *            The DTO representing the session to join
     */
    void joinSession(GameSessionDTO session);

    /**
     * This method is called by the GUI in order to ask the client to start the joined session
     */
    void startSession();

    /**
     * This method is called by the GUI in order to ask the client to leave the joined session
     */
    void leaveSession();

    /**
     * This method is called by the GUI in order to ask the client to move the snake on the gameboard
     * 
     * @param direction
     *            The direction where to move the snake
     */
    void moveSnake(MoveDirection direction);

}
