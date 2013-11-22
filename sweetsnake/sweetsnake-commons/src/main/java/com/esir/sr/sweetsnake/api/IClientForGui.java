package com.esir.sr.sweetsnake.api;

import com.esir.sr.sweetsnake.dto.GameSessionDTO;
import com.esir.sr.sweetsnake.dto.PlayerDTO;
import com.esir.sr.sweetsnake.enumeration.MoveDirection;
import com.esir.sr.sweetsnake.exception.UnableToConnectException;

public interface IClientForGui extends IClient
{

    /**********************************************************************************************
     * [BLOCK] GUI EXPOSED METHODS
     **********************************************************************************************/

    /**
     * from the gui
     * 
     */
    void reachServer();

    /**
     * from the gui
     * 
     */
    void connect(String username) throws UnableToConnectException;

    /**
     * from the gui
     * 
     */
    void disconnect();

    /**
     * from the gui
     * 
     * @param player
     */
    void sendRequest(PlayerDTO player);

    /**
     * 
     */
    void createSession();

    /**
     * 
     * @param session
     */
    void joinSession(GameSessionDTO session);

    /**
     * 
     */
    void readyToPlay();

    /**
     * from the gui
     * 
     */
    void startSession();

    /**
     * from the gui
     * 
     */
    void leaveSession();

    /**
     * from the gui
     * 
     * @param direction
     */
    void moveSnake(MoveDirection direction);

}
