package com.esir.sr.sweetsnake.api;

import java.rmi.Remote;
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
public interface IClientCallback extends Remote
{

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
     * 
     * @param players
     * @throws RemoteException
     */
    void refreshPlayersList(List<PlayerDTO> players) throws RemoteException;

    /**
     * 
     * @param sessions
     * @throws RemoteException
     */
    void refreshSessionsList(List<GameSessionDTO> sessions) throws RemoteException;

    /**
     * 
     * @param request
     * @throws RemoteException
     */
    void requestSent(GameRequestDTO request) throws RemoteException;

    /**
     * 
     * @param request
     * @throws RemoteException
     */
    void requestReceived(GameRequestDTO request) throws RemoteException;

    /**
     * 
     * @param request
     * @throws RemoteException
     */
    void requestDenied(GameRequestDTO request) throws RemoteException;

    /**
     * 
     * @param playerNb
     * @param session
     * @throws RemoteException
     */
    void sessionJoined(int playerNb, GameSessionDTO session) throws RemoteException;

    /**
     * 
     * @param playerNb
     * @param session
     * @throws RemoteException
     */
    void sessionStarted(int playerNb, GameSessionDTO session) throws RemoteException;

    /**
     * 
     * @param session
     * @param leaver
     * @param stopped
     * @param finished
     * @throws RemoteException
     */
    void sessionLeft(GameSessionDTO session, PlayerDTO leaver, boolean stopped, boolean finished) throws RemoteException;

    /**
     * 
     * @param session
     * @throws RemoteException
     */
    void sessionFinished(GameSessionDTO session) throws RemoteException;

    /**
     * 
     * @param session
     * @throws RemoteException
     */
    void refreshSession(GameSessionDTO session) throws RemoteException;

    /**
     * 
     * @return
     * @throws RemoteException
     */
    String getName() throws RemoteException;

}
