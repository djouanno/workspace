package com.esir.sr.sweetsnake.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.esir.sr.sweetsnake.enumeration.MoveDirection;
import com.esir.sr.sweetsnake.exception.UnauthorizedActionException;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public interface IGameSessionCallback extends Remote
{

    /**
     * 
     * @param client
     * @throws UnauthorizedActionException
     * @throws RemoteException
     */
    void startGame(IClientCallback client) throws UnauthorizedActionException, RemoteException;

    /**
     * 
     * @param client
     * @throws RemoteException
     */
    void leaveGame(IClientCallback client) throws RemoteException;

    /**
     * 
     * @param client
     * @param direction
     * @throws UnauthorizedActionException
     * @throws RemoteException
     */
    void move(IClientCallback client, MoveDirection direction) throws UnauthorizedActionException, RemoteException;

}
