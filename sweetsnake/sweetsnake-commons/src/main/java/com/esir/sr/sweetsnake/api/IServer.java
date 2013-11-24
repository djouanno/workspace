package com.esir.sr.sweetsnake.api;

import com.esir.sr.sweetsnake.dto.GameRequestDTO;
import com.esir.sr.sweetsnake.dto.GameSessionDTO;
import com.esir.sr.sweetsnake.dto.PlayerDTO;
import com.esir.sr.sweetsnake.exception.GameRequestNotFoundException;
import com.esir.sr.sweetsnake.exception.GameSessionNotFoundException;
import com.esir.sr.sweetsnake.exception.MaximumNumberOfPlayersException;
import com.esir.sr.sweetsnake.exception.PlayerNotAvailableException;
import com.esir.sr.sweetsnake.exception.PlayerNotFoundException;
import com.esir.sr.sweetsnake.exception.UnableToConnectException;
import com.esir.sr.sweetsnake.exception.UnauthorizedActionException;

/**
 * This interface represents which methods the server must be able to provide to a client.<br />
 * All the methods below are intented to be called by the client according to the events he processed.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public interface IServer
{

    /**********************************************************************************************
     * [BLOCK] CONNECTION METHODS
     **********************************************************************************************/

    /**
     * This method is called by the client in order to connect to the server
     * 
     * @param client
     *            The client callback
     * @throws UnableToConnectException
     *             If the client username is malformed or already taken by someone else
     */
    void connect(IClientCallback client) throws UnableToConnectException;

    /**
     * This method is called by the client in order to disconnect from the server
     * 
     * @param client
     *            The client callback
     */
    void disconnect(IClientCallback client);

    /**********************************************************************************************
     * [BLOCK] GAME REQUESTS METHODS
     **********************************************************************************************/

    /**
     * This method is called by the client in order to send a game request to another player
     * 
     * @param client
     *            The client callback
     * @param otherPlayer
     *            The DTO representing the invited player
     * @throws PlayerNotFoundException
     *             If the other player is no more connected to the server
     * @throws PlayerNotAvailableException
     *             If the player is busy in another game session
     */
    void sendRequest(IClientCallback client, PlayerDTO otherPlayer) throws PlayerNotFoundException, PlayerNotAvailableException;

    /**
     * This method is called by the client in order to cancel a sent game request to another player
     * 
     * @param client
     *            The client callback
     * @throws GameRequestNotFoundException
     *             If the request is no more cancelable
     */
    void cancelRequest(IClientCallback client, GameRequestDTO requestDTO) throws GameRequestNotFoundException;

    /**
     * This method is called by the client in order to accept a received game request from another player
     * 
     * @param client
     *            The client callback
     * @param requestDto
     *            The DTO representing the received request
     * @throws GameRequestNotFoundException
     *             If the request is no more available
     * @throws GameSessionNotFoundException
     *             If the session is no more available
     * @throws MaximumNumberOfPlayersException
     *             If the maximum number of players in the session has been reached
     */
    void acceptRequest(IClientCallback client, GameRequestDTO requestDto) throws GameRequestNotFoundException, GameSessionNotFoundException, MaximumNumberOfPlayersException;

    /**
     * This method is called by the client in order to deny a received game request from another player
     * 
     * @param client
     *            The client callback
     * @param requestDto
     *            The DTO representing the received request
     * @throws GameRequestNotFoundException
     *             If the requestis no more available
     */
    void denyRequest(IClientCallback client, GameRequestDTO requestDto) throws GameRequestNotFoundException;

    /**********************************************************************************************
     * [BLOCK] GAME SESSIONS METHODS
     **********************************************************************************************/

    /**
     * This method is called by the client in order to create a new game session
     * 
     * @param client
     *            The client callback
     * @throws UnauthorizedActionException
     *             If the client is already taking part in another game session
     */
    void createSession(IClientCallback client) throws UnauthorizedActionException;

    /**
     * This method is called by the client in order to join an existing game session
     * 
     * @param client
     *            The client callback
     * @param sessionDto
     *            The DTO representing the game session
     * @throws GameSessionNotFoundException
     *             If the game session is no more available
     * @throws MaximumNumberOfPlayersException
     *             If the maximum number of players in the session has been reached
     */
    void joinSession(IClientCallback client, GameSessionDTO sessionDto) throws GameSessionNotFoundException, MaximumNumberOfPlayersException;

}
