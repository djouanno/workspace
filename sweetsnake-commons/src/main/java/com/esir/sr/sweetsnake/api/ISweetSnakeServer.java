package com.esir.sr.sweetsnake.api;

import java.util.List;

import com.esir.sr.sweetsnake.dto.SweetSnakeGameRequestDTO;
import com.esir.sr.sweetsnake.dto.SweetSnakeGameSessionDTO;
import com.esir.sr.sweetsnake.dto.SweetSnakePlayerDTO;
import com.esir.sr.sweetsnake.enumeration.Direction;
import com.esir.sr.sweetsnake.exception.GameRequestNotFoundException;
import com.esir.sr.sweetsnake.exception.GameSessionNotFoundException;
import com.esir.sr.sweetsnake.exception.PlayerNotAvailableException;
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
     * @throws PlayerNotFoundException
     */
    void disconnect(ISweetSnakeClientCallback client) throws PlayerNotFoundException;

    /**
     * 
     * @param client
     * @param otherPlayer
     * @return
     * @throws PlayerNotFoundException
     * @throws PlayerNotAvailableException
     */
    SweetSnakeGameRequestDTO requestGameSession(ISweetSnakeClientCallback client, SweetSnakePlayerDTO otherPlayer) throws PlayerNotFoundException, PlayerNotAvailableException;

    /**
     * 
     * @param client
     * @param request
     * @return
     * @throws PlayerNotFoundException
     * @throws GameRequestNotFoundException
     */
    SweetSnakeGameSessionDTO acceptGameSession(ISweetSnakeClientCallback client, SweetSnakeGameRequestDTO requestDTO) throws PlayerNotFoundException, GameRequestNotFoundException;

    /**
     * 
     * @param client
     */
    void leaveGameSession(ISweetSnakeClientCallback client);

    /**
     * 
     * @param client
     * @throws PlayerNotFoundException
     * @throws GameRequestNotFoundException
     */
    void cancelGameRequest(ISweetSnakeClientCallback client, SweetSnakeGameRequestDTO requestDTO) throws PlayerNotFoundException, GameRequestNotFoundException;

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
     * @throws PlayerNotFoundException
     * @throws GameSessionNotFoundException
     */
    void requestMove(ISweetSnakeClientCallback client, SweetSnakeGameSessionDTO session, Direction direction) throws PlayerNotFoundException, GameSessionNotFoundException;

}
