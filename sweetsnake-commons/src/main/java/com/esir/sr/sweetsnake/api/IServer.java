package com.esir.sr.sweetsnake.api;

import java.util.List;

import com.esir.sr.sweetsnake.dto.GameRequestDTO;
import com.esir.sr.sweetsnake.dto.GameSessionDTO;
import com.esir.sr.sweetsnake.dto.PlayerDTO;
import com.esir.sr.sweetsnake.enumeration.MoveDirection;
import com.esir.sr.sweetsnake.exception.GameRequestNotFoundException;
import com.esir.sr.sweetsnake.exception.GameSessionNotFoundException;
import com.esir.sr.sweetsnake.exception.PlayerNotAvailableException;
import com.esir.sr.sweetsnake.exception.PlayerNotFoundException;
import com.esir.sr.sweetsnake.exception.UnableToConnectException;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public interface IServer
{

    /**
     * 
     * @param client
     * @throws UnableToConnectException
     */
    void connect(IClientCallback client) throws UnableToConnectException;

    /**
     * 
     * @param client
     * @throws PlayerNotFoundException
     */
    void disconnect(IClientCallback client) throws PlayerNotFoundException;

    /**
     * 
     * @param client
     * @param otherPlayer
     * @return
     * @throws PlayerNotFoundException
     * @throws PlayerNotAvailableException
     */
    GameRequestDTO requestGame(IClientCallback client, PlayerDTO otherPlayer) throws PlayerNotFoundException, PlayerNotAvailableException;

    /**
     * 
     * @param client
     * @param request
     * @return
     * @throws PlayerNotFoundException
     * @throws GameRequestNotFoundException
     */
    GameSessionDTO acceptGame(IClientCallback client, GameRequestDTO requestDTO) throws PlayerNotFoundException, GameRequestNotFoundException;

    /**
     * 
     * @param client
     * @param requestDTO
     * @throws GameRequestNotFoundException
     */
    void refuseGame(IClientCallback client, GameRequestDTO requestDTO) throws GameRequestNotFoundException;

    /**
     * 
     * @param client
     */
    void leaveGame(IClientCallback client);

    /**
     * 
     * @param client
     * @throws PlayerNotFoundException
     * @throws GameRequestNotFoundException
     */
    void cancelGameRequest(IClientCallback client, GameRequestDTO requestDTO) throws PlayerNotFoundException, GameRequestNotFoundException;

    /**
     * 
     * @param client
     * @return
     */
    List<PlayerDTO> getPlayersList(IClientCallback client);

    /**
     * 
     * @param client
     * @param session
     * @param direction
     * @throws PlayerNotFoundException
     * @throws GameSessionNotFoundException
     */
    void requestMove(IClientCallback client, GameSessionDTO session, MoveDirection direction) throws PlayerNotFoundException, GameSessionNotFoundException;

}
