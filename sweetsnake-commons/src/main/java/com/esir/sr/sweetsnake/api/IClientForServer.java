package com.esir.sr.sweetsnake.api;

import java.rmi.RemoteException;
import java.util.List;

import com.esir.sr.sweetsnake.dto.GameRequestDTO;
import com.esir.sr.sweetsnake.dto.GameSessionDTO;
import com.esir.sr.sweetsnake.dto.PlayerDTO;

/**
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
     * @throws RemoteException
     * 
     */
    void connected() throws RemoteException;

    /**
     * @throws RemoteException
     * 
     */
    void disconnected() throws RemoteException;

    /**
     * from the server
     * 
     * @param playersList
     */
    void refreshPlayersList(List<PlayerDTO> playersList);

    /**
     * 
     * @param sessionsList
     */
    void refreshSessionsList(List<GameSessionDTO> sessionsList);

    /**
     * from the server
     * 
     * @param request
     */
    void requestSent(GameRequestDTO request);

    /**
     * from the server
     * 
     * @param request
     */
    void requestReceived(GameRequestDTO request);

    /**
     * from the server
     * 
     * @param request
     */
    void requestDenied(GameRequestDTO request);

    /**
     * from the server
     * 
     * @param playerNb
     * @param session
     */
    void sessionJoined(int playerNb, GameSessionDTO session);

    /**
     * from the server
     * 
     * @param playerNb
     * @param session
     */
    void sessionStarted(int playerNb, GameSessionDTO session);

    /**
     * from the server
     * 
     * @param session
     * @param leaver
     * @param stopped
     * @param finished
     */
    void sessionLeft(GameSessionDTO session, PlayerDTO leaver, boolean stopped, boolean finished);

    /**
     * 
     * @param session
     */
    void sessionFinished(GameSessionDTO session);

    /**
     * from the server
     * 
     * @param session
     */
    void refreshSession(GameSessionDTO session);

}
