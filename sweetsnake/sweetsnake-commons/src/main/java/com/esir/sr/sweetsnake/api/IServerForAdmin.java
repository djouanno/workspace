package com.esir.sr.sweetsnake.api;

import com.esir.sr.sweetsnake.dto.GameRequestDTO;
import com.esir.sr.sweetsnake.dto.GameSessionDTO;
import com.esir.sr.sweetsnake.dto.PlayerDTO;

/**
 * This interface represents which methods the server must be able to provide to an administrator.<br />
 * All the methods below are intented to be called by the administrator according to the events he processed.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public interface IServerForAdmin
{

    /**
     * This method is called by the admin in order to start the server, ie bind the RMI service
     */
    void startServer();

    /**
     * This method is called by the admin in order to stop the server, ie unbind the RMI service
     */
    void stopServer();

    /**
     * This method is called by the admin in order to kick a player from the server
     * 
     * @param playerDto
     *            The DTO representing the player to kick
     */
    void kickPlayer(PlayerDTO playerDto);

    /**
     * This method is called by the admin in order to stop a running game session
     * 
     * @param sessionDto
     *            The DTO representing the session to stop
     */
    void stopSession(GameSessionDTO sessionDto);

    /**
     * This method is called by the admin in order to remove a game session
     * 
     * @param sessionDto
     *            The DTO representing the session to remove
     */
    void removeSession(GameSessionDTO sessionDto);

    /**
     * This method is called by the admin in order to remove a pending game request
     * 
     * @param requestDto
     *            The DTO representing the request to remove
     */
    void removeRequest(GameRequestDTO requestDto);

}
