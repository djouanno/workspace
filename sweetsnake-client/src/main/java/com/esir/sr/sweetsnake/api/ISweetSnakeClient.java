package com.esir.sr.sweetsnake.api;

import com.esir.sr.sweetsnake.dto.SweetSnakeGameRequestDTO;
import com.esir.sr.sweetsnake.dto.SweetSnakeGameSessionDTO;

public interface ISweetSnakeClient
{

    /**
     * 
     */
    void connect();

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
     * @param element
     */
    void addElement(IElement element);

    /**
     * 
     * @param element
     */
    void updateElement(IElement element);

    /**
     * 
     * @param element
     */
    void removeElement(IElement element);

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
     * @param name
     */
    void setName(String name);

    /**
     * 
     * @param score
     */
    void setScore(long score);

}
