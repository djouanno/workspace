package com.esir.sr.sweetsnake.api;

import com.esir.sr.sweetsnake.dto.GameBoardDTO;

/**
 * 
 * This interface represents which methods a Graphic User Interface for the game must be able to provide.<br />
 * All the methods below are intented to be called by the client according to the events it processed.
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
    void connectedToServer();

    /**
     * 
     * @param gameBoard
     */
    void gameStarted(GameBoardDTO gameBoard);

    /**
     * 
     */
    void gameLeaved();

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

}
