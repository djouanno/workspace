package com.esir.sr.sweetsnake.api;

import java.util.List;

import com.esir.sr.sweetsnake.dto.SweetSnakeGameRequestDTO;
import com.esir.sr.sweetsnake.dto.SweetSnakeGameSessionDTO;
import com.esir.sr.sweetsnake.dto.SweetSnakePlayerDTO;
import com.esir.sr.sweetsnake.enumeration.Direction;
import com.esir.sr.sweetsnake.exception.PlayerNotFoundException;
import com.esir.sr.sweetsnake.exception.UnableToConnectException;
import com.esir.sr.sweetsnake.exception.UnableToMountGameSessionException;

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
     * @throws UnableToMountGameSessionException
     */
    SweetSnakeGameRequestDTO requestGameSession(ISweetSnakeClientCallback client, SweetSnakePlayerDTO otherPlayer) throws PlayerNotFoundException, UnableToMountGameSessionException;

    /**
     * 
     * @param client
     * @param request
     * @return
     * @throws PlayerNotFoundException
     * @throws UnableToMountGameSessionException
     */
    SweetSnakeGameSessionDTO acceptGameSession(ISweetSnakeClientCallback client, SweetSnakeGameRequestDTO request) throws PlayerNotFoundException, UnableToMountGameSessionException;

    /**
     * 
     * @param client
     */
    void leaveGameSession(ISweetSnakeClientCallback client);

    /**
     * 
     * @param client
     */
    void cancelGameSessionRequest(ISweetSnakeClientCallback client);

    /**
     * 
     * @param client
     * @return
     */
    List<SweetSnakePlayerDTO> getPlayersList(ISweetSnakeClientCallback client);

    /**
     * 
     * @param direction
     */
    void move(Direction direction);

}
