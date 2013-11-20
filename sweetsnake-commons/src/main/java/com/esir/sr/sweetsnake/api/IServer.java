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
     * 
     * @param client
     * @throws UnableToConnectException
     */
    void connect(IClientCallback client) throws UnableToConnectException;

    /**
     * 
     * @param client
     */
    void disconnect(IClientCallback client);

    /**********************************************************************************************
     * [BLOCK] GAME REQUESTS METHODS
     **********************************************************************************************/

    /**
     * 
     * @param client
     * @param otherPlayer
     * @throws PlayerNotFoundException
     * @throws PlayerNotAvailableException
     * @throws GameSessionNotFoundException
     */
    void sendRequest(IClientCallback client, PlayerDTO otherPlayer) throws PlayerNotFoundException, PlayerNotAvailableException, GameSessionNotFoundException;

    /**
     * 
     * @param client
     * @throws GameRequestNotFoundException
     */
    void cancelRequest(IClientCallback client, GameRequestDTO requestDTO) throws GameRequestNotFoundException;

    /**
     * 
     * @param client
     * @param request
     * @throws GameRequestNotFoundException
     * @throws GameSessionNotFoundException
     * @throws MaximumNumberOfPlayersException
     */
    void acceptRequest(IClientCallback client, GameRequestDTO requestDTO) throws GameRequestNotFoundException, GameSessionNotFoundException, MaximumNumberOfPlayersException;

    /**
     * 
     * @param client
     * @param requestDTO
     * @throws GameRequestNotFoundException
     */
    void denyRequest(IClientCallback client, GameRequestDTO requestDTO) throws GameRequestNotFoundException;

    /**********************************************************************************************
     * [BLOCK] GAME SESSIONS METHODS
     **********************************************************************************************/

    /**
     * 
     * @param client
     * @throws UnauthorizedActionException
     */
    void createSession(IClientCallback client) throws UnauthorizedActionException;

    /**
     * 
     * @param client
     * @param sessionDTO
     * @throws GameSessionNotFoundException
     * @throws MaximumNumberOfPlayersException
     */
    void joinSession(IClientCallback client, GameSessionDTO sessionDTO) throws GameSessionNotFoundException, MaximumNumberOfPlayersException;

}
