package com.esir.sr.sweetsnake.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.esir.sr.sweetsnake.dto.GameRequestDTO;
import com.esir.sr.sweetsnake.dto.GameSessionDTO;
import com.esir.sr.sweetsnake.enumeration.MoveDirection;

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
     * @param request
     * @throws RemoteException
     */
    void gameRequested(GameRequestDTO request) throws RemoteException;

    /**
     * 
     * @param request
     * @throws RemoteException
     */
    void gameRefused(GameRequestDTO request) throws RemoteException;

    /**
     * 
     * @param session
     * @throws RemoteException
     */
    void gameStarted(GameSessionDTO session) throws RemoteException;

    /**
     * 
     * @param session
     * @throws RemoteException
     */
    void gameLeaved(GameSessionDTO session) throws RemoteException;

    /**
     * 
     * @param direction
     * @throws RemoteException
     */
    void moveConfirmed(MoveDirection direction) throws RemoteException;

    /**
     * 
     * @param score
     * @throws RemoteException
     */
    void setScore(long score) throws RemoteException;

    /**
     * 
     * @return
     * @throws RemoteException
     */
    String getUsername() throws RemoteException;

}
