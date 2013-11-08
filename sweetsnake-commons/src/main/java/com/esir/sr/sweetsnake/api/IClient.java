package com.esir.sr.sweetsnake.api;

import java.util.List;

import com.esir.sr.sweetsnake.dto.GameRequestDTO;
import com.esir.sr.sweetsnake.dto.GameSessionDTO;
import com.esir.sr.sweetsnake.dto.PlayerDTO;
import com.esir.sr.sweetsnake.enumeration.MoveDirection;
import com.esir.sr.sweetsnake.enumeration.PlayerStatus;
import com.esir.sr.sweetsnake.exception.UnableToConnectException;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public interface IClient
{

    /**
     * 
     */
    void reachServer();

    /**
     * 
     */
    void connect(String username) throws UnableToConnectException;

    /**
     * 
     */
    void disconnect();

    /**
     * from the ihm
     * 
     * @param player
     */
    void requestGame(PlayerDTO player);

    /**
     * from the server
     * 
     * @param request
     */
    void requestGame(GameRequestDTO request);

    /**
     * 
     * @param request
     */
    void requestRefused(GameRequestDTO request);

    /**
     * 
     * @param session
     */
    void startGame(GameSessionDTO session);

    /**
     * 
     * @param direction
     */
    void confirmMove(MoveDirection direction);

    /**
     * 
     * @return
     */
    String getUsername();

    /**
     * 
     * @return
     */
    PlayerStatus getStatus();

    /**
     * 
     * @return
     */
    long getScore();

    /**
     * 
     * @param score
     */
    void setScore(long score);

    /**
     * 
     * @return
     */
    List<PlayerDTO> getPlayersList();

}
