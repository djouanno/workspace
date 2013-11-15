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
     * 
     * @param players
     * @throws RemoteException
     */
    void refreshPlayersList(List<PlayerDTO> players) throws RemoteException;

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
     * @param allDenied
     * @param request
     * @throws RemoteException
     */
    void requestDenied(boolean allDenied, GameRequestDTO request) throws RemoteException;

    /**
     * 
     * @param session
     * @throws RemoteException
     */
    void sessionJoined(GameSessionDTO session) throws RemoteException;

    /**
     * 
     * @param session
     * @throws RemoteException
     */
    void sessionStarted(GameSessionDTO session) throws RemoteException;

    /**
     * 
     * @param session
     * @param leaver
     * @throws RemoteException
     */
    void sessionLeft(GameSessionDTO session, PlayerDTO leaver) throws RemoteException;

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
