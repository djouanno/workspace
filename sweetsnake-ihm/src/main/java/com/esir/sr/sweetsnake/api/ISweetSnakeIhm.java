package com.esir.sr.sweetsnake.api;

import com.esir.sr.sweetsnake.dto.SweetSnakePlayerDTO;
import com.esir.sr.sweetsnake.enumeration.SweetSnakeDirection;

public interface ISweetSnakeIhm
{

    /**
     * 
     */
    void serverReachable();

    /**
     * 
     */
    void serverNotReachable();

    /**
     * 
     */
    void successfullyConnected();

    /**
     * 
     * @param requestedPlayer
     */
    void requestGame(SweetSnakePlayerDTO requestedPlayer);

    /**
     * 
     */
    void startGame(/* TODO send game map */);

    /**
     * 
     * @param direction
     */
    void moveSnake(SweetSnakeDirection direction);

    /**
     * 
     * @param message
     */
    void displayInfoMessage(String message);

    /**
     * 
     * @param message
     */
    void displayErrorMessage(String message);

    /**
     * 
     * @param message
     * @return
     */
    int displayConfirmMessage(String message);

    /**
     * 
     */
    void refreshUI();

}
