package com.esir.sr.sweetsnake.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.esir.sr.sweetsnake.dto.GameRequestDTO;
import com.esir.sr.sweetsnake.dto.GameSessionDTO;
import com.esir.sr.sweetsnake.dto.PlayerDTO;

/**
 * This interface represents which methods a client must be able to provide to a server.<br />
 * All the methods below are intented to be called by the server according to the events it processed.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public interface IClientCallback extends Remote
{

    /**
     * This method is called by the server in order to notify the client it has successfully connected
     * 
     * @throws RemoteException
     *             If the RMI fails
     * 
     */
    void connected() throws RemoteException;

    /**
     * This method is called by the server in order to notify the client it has successfully disconnected
     * 
     * @throws RemoteException
     *             If the RMI fails
     * 
     */
    void disconnected() throws RemoteException;

    /**
     * This method is called by the server in order to notify the client to refresh the connected players
     * 
     * @param players
     *            A list containing the DTO representing all the connected players
     * @throws RemoteException
     *             If the RMI fails
     */
    void refreshPlayersList(List<PlayerDTO> players) throws RemoteException;

    /**
     * This method is called by the server in order to notify the client to refresh the available sessions
     * 
     * @param sessions
     *            A list containing the DTO representing all the available sessions
     * @throws RemoteException
     *             If the RMI fails
     */
    void refreshSessionsList(List<GameSessionDTO> sessions) throws RemoteException;

    /**
     * This method is called by the server in order to notify the client its request was succesfully sent to another client
     * 
     * @param request
     *            The DTO representing the sent request
     * @throws RemoteException
     *             If the RMI fails
     */
    void requestSent(GameRequestDTO request) throws RemoteException;

    /**
     * This method is called by the server in order to notify the client it has received a request from another client
     * 
     * @param request
     *            The DTO representing the received request
     * @throws RemoteException
     *             If the RMI fails
     */
    void requestReceived(GameRequestDTO request) throws RemoteException;

    /**
     * This method is called by the server in order to notify the client its sent request was denied by the other client
     * 
     * @param request
     *            The DTO representing the received request
     * @throws RemoteException
     *             If the RMI fails
     */
    void requestDenied(GameRequestDTO request) throws RemoteException;

    /**
     * This method is called by the server in order to notify the client it has succesfully joined a session or another client has
     * joined the session
     * 
     * @param playerNb
     *            The client player number
     * @param session
     *            The DTO representing the joined session
     * @throws RemoteException
     *             If the RMI fails
     */
    void sessionJoined(int playerNb, GameSessionDTO session) throws RemoteException;

    /**
     * This method is called by the server in order to notify the client the session it takes part of has started
     * 
     * @param session
     *            The DTO representing the session
     * @throws RemoteException
     *             If the RMI fails
     */
    void sessionStarted(GameSessionDTO session) throws RemoteException;

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
     * @throws RemoteException
     *             If the RMI fails
     */
    void sessionLeft(GameSessionDTO session, PlayerDTO leaver, boolean stopped, boolean finished) throws RemoteException;

    /**
     * This method is called by the server in order to notify the client the session it takes part of has been finished
     * 
     * @param session
     *            The DTO representing the session
     * @throws RemoteException
     *             If the RMI fails
     */
    void sessionFinished(GameSessionDTO session) throws RemoteException;

    /**
     * This method is called by the server in order to notify the client the session it takes part of need to be refreshed
     * 
     * @param session
     *            The DTO representing the session
     * @throws RemoteException
     *             If the RMI fails
     */
    void refreshSession(GameSessionDTO session) throws RemoteException;

    /**
     * This method returns the username chosen by the client during the connection
     * 
     * @return A String containing the username chosen by the client
     * @throws RemoteException
     *             If the RMI fails
     */
    String getUsername() throws RemoteException;

}
