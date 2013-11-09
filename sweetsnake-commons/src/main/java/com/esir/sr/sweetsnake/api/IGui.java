package com.esir.sr.sweetsnake.api;

import com.esir.sr.sweetsnake.dto.PlayerDTO;
import com.esir.sr.sweetsnake.enumeration.MoveDirection;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public interface IGui
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
    void requestGame(PlayerDTO requestedPlayer);

    /**
     * 
     */
    void startGame(/* TODO send game map */);

    /**
     * 
     * @param direction
     */
    void moveSnake(MoveDirection direction);

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
     * @param buttons
     * @return
     */
    int displayCustomMessage(String message, String[] buttons);

    /**
     * 
     */
    void refreshUI();

}
