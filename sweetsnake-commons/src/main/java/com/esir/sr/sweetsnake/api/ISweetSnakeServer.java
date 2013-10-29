package com.esir.sr.sweetsnake.api;

import java.util.List;

import com.esir.sr.sweetsnake.dto.SweetSnakeGameRequestDTO;
import com.esir.sr.sweetsnake.dto.SweetSnakeGameSessionDTO;
import com.esir.sr.sweetsnake.dto.SweetSnakePlayerDTO;
import com.esir.sr.sweetsnake.enumeration.Direction;
import com.esir.sr.sweetsnake.exception.BadGameSessionException;
import com.esir.sr.sweetsnake.exception.PlayerNotFoundException;
import com.esir.sr.sweetsnake.exception.UnableToConnectException;

public interface ISweetSnakeServer
{

    /**
     * 
     * @param client
     * @throws UnableToConnectException
     */
    void connect(ISweetSnakeClientCallback client) throws UnableToConnectException;

    /**
     * 
     * @param client
     */
    void disconnect(ISweetSnakeClientCallback client);

    /**
     * 
     * @param client
     * @param otherPlayer
     * @return
     * @throws PlayerNotFoundException
     * @throws BadGameSessionException
     */
    SweetSnakeGameRequestDTO requestGameSession(ISweetSnakeClientCallback client, SweetSnakePlayerDTO otherPlayer) throws PlayerNotFoundException, BadGameSessionException;

    /**
     * 
     * @param client
     * @param request
     * @return
     * @throws PlayerNotFoundException
     * @throws BadGameSessionException
     */
    SweetSnakeGameSessionDTO acceptGameSession(ISweetSnakeClientCallback client, SweetSnakeGameRequestDTO request) throws PlayerNotFoundException, BadGameSessionException;

    /**
     * 
     * @param client
     */
    void leaveGameSession(ISweetSnakeClientCallback client);

    /**
     * 
     * @param client
     */
    void cancelGameRequest(ISweetSnakeClientCallback client);

    /**
     * 
     * @param client
     * @return
     */
    List<SweetSnakePlayerDTO> getPlayersList(ISweetSnakeClientCallback client);

    /**
     * 
     * @param client
     * @param session
     * @param direction
     */
    void requestMove(ISweetSnakeClientCallback client, SweetSnakeGameSessionDTO session, Direction direction) throws BadGameSessionException;

}
