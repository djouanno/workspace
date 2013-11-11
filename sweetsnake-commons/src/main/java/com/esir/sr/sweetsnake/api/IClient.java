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
     * from the gui
     * 
     * @param player
     */
    void requestGame(PlayerDTO player);

    /**
     * from the server
     * 
     * @param request
     */
    void gameRequested(GameRequestDTO request);

    /**
     * 
     * @param request
     */
    void gameRefused(GameRequestDTO request);

    /**
     * 
     */
    void startGame();

    /**
     * 
     * @param session
     */
    void gameStarted(GameSessionDTO session);

    /**
     * 
     */
    void leaveGame();

    /**
     * 
     * @param session
     */
    void gameLeaved(GameSessionDTO session);

    /**
     * 
     * @param direction
     */
    void moveSnake(MoveDirection direction);

    /**
     * 
     * @param direction
     */
    void snakeMoved(MoveDirection direction);

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
