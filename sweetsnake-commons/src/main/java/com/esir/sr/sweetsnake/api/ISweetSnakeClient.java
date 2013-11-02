package com.esir.sr.sweetsnake.api;

import java.util.List;

import com.esir.sr.sweetsnake.dto.SweetSnakeGameRequestDTO;
import com.esir.sr.sweetsnake.dto.SweetSnakeGameSessionDTO;
import com.esir.sr.sweetsnake.enumeration.SweetSnakeDirection;
import com.esir.sr.sweetsnake.exception.UnableToConnectException;

public interface ISweetSnakeClient
{

    /**
     * 
     */
    void connect(String username) throws UnableToConnectException;

    /**
     * 
     */
    void disconnect();

    /**
     * 
     * @param request
     */
    void requestGame(SweetSnakeGameRequestDTO request);

    /**
     * 
     * @param session
     */
    void startGame(SweetSnakeGameSessionDTO session);

    /**
     * 
     * @param direction
     */
    void confirmMove(SweetSnakeDirection direction);

    /**
     * 
     * @return
     */
    String getName();

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
    List<String> getPlayersList();

}
