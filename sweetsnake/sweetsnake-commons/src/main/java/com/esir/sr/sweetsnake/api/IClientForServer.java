package com.esir.sr.sweetsnake.api;

import java.util.List;

import com.esir.sr.sweetsnake.dto.GameRequestDTO;
import com.esir.sr.sweetsnake.dto.GameSessionDTO;
import com.esir.sr.sweetsnake.dto.PlayerDTO;

/**
 * This interface represents which methods a client must be able to provide to a sever.<br />
 * All the methods below are intented to be called by the server according to the events it processed.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public interface IClientForServer extends IClient
{

    /**********************************************************************************************
     * [BLOCK] SERVER EXPOSED METHODS
     **********************************************************************************************/

    /**
     * This method is called by the server in order to notify the client it has successfully connected
     */
    void connected();

    /**
     * This method is called by the server in order to notify the client it has successfully disconnected
     */
    void disconnected();

    /**
     * This method is called by the server in order to notify the client to refresh the connected players
     * 
     * @param players
     *            A list containing the DTO representing all the connected players
     */
    void refreshPlayersList(List<PlayerDTO> players);

    /**
     * This method is called by the server in order to notify the client to refresh the available sessions
     * 
     * @param sessions
     *            A list containing the DTO representing all the available sessions
     */
    void refreshSessionsList(List<GameSessionDTO> sessions);

    /**
     * This method is called by the server in order to notify the client its request was succesfully sent to another client
     * 
     * @param request
     *            The DTO representing the sent request
     */
    void requestSent(GameRequestDTO request);

    /**
     * This method is called by the server in order to notify the client it has received a request from another client
     * 
     * @param request
     *            The DTO representing the received request
     */
    void requestReceived(GameRequestDTO request);

    /**
     * This method is called by the server in order to notify the client its sent request was denied by the other client
     * 
     * @param request
     *            The DTO representing the received request
     */
    void requestDenied(GameRequestDTO request);

    /**
     * This method is called by the server in order to notify the client it has succesfully joined a session or another client has
     * joined the session
     * 
     * @param playerNb
     *            The client player number
     * @param session
     *            The DTO representing the joined session
     */
    void sessionJoined(int playerNb, GameSessionDTO session);

    /**
     * This method is called by the server in order to notify the client the session it takes part of has started
     * 
     * @param session
     *            The DTO representing the session
     */
    void sessionStarted(GameSessionDTO session);

    /**
     * This method is called by the server in order to notify the client it has succesfully left a session or another client has
     * left the session
     * 
     * @param session
     *            The DTO representing the session
     * @param leaver
     *            The DTO representing the leaving player
     * @param stopped
     *            Whether the session is stopped or not
     * @param finished
     *            Whether the session is finished or not (ie removed)
     */
    void sessionLeft(GameSessionDTO session, PlayerDTO leaver, boolean stopped, boolean finished);

    /**
     * This method is called by the server in order to notify the client the session it takes part of has been finished
     * 
     * @param session
     *            The DTO representing the session
     */
    void sessionFinished(GameSessionDTO session);

    /**
     * This method is called by the server in order to notify the client the session it takes part of need to be refreshed
     * 
     * @param session
     *            The DTO representing the session
     */
    void refreshSession(GameSessionDTO session);

}
