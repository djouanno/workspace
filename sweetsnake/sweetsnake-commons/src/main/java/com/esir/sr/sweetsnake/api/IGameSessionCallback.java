package com.esir.sr.sweetsnake.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.esir.sr.sweetsnake.enumeration.MoveDirection;
import com.esir.sr.sweetsnake.exception.UnauthorizedActionException;

/**
 * This interface represents which methods a game session must be able to provide to a client.<br />
 * All the methods below are intented to be called by the server according to the events it processed.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public interface IGameSessionCallback extends Remote
{

    /**
     * This method is called by the client in order to change its number in the session
     * 
     * @param client
     *            The client callback
     * @param number
     *            The requested number
     * @throws RemoteException
     *             If the RMI fails
     */
    void changeNumber(IClientCallback client, int number) throws RemoteException;

    /**
     * This method is called by the client in order to ask for a game session to start
     * 
     * @param client
     *            The client callback
     * @throws UnauthorizedActionException
     *             If the client is not authorized to start the session
     * @throws RemoteException
     *             If the RMI fails
     */
    void startGame(IClientCallback client) throws UnauthorizedActionException, RemoteException;

    /**
     * This method is called by the client in order to ask for leaving a game session
     * 
     * @param client
     *            The client callback
     * @throws RemoteException
     *             If the RMI fails
     */
    void leaveGame(IClientCallback client) throws RemoteException;

    /**
     * This method is called by the client in order to ask for a snake move in the game session
     * 
     * @param client
     *            The client callback
     * @param direction
     *            The direction where to move the snake
     * @throws RemoteException
     *             If the RMI fails
     */
    void move(IClientCallback client, MoveDirection direction) throws RemoteException;

}
