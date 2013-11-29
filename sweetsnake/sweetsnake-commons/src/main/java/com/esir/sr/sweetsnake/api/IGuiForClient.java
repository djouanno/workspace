package com.esir.sr.sweetsnake.api;

import java.util.List;

import com.esir.sr.sweetsnake.dto.GameRequestDTO;
import com.esir.sr.sweetsnake.dto.GameSessionDTO;
import com.esir.sr.sweetsnake.dto.PlayerDTO;

/**
 * 
 * This interface represents which methods a GUI for the game must be able to provide to a client.<br />
 * All the methods below are intented to be called by the client according to the events it processed.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public interface IGuiForClient
{

    /**
     * This method is called by the client in order to notify the GUI that the server is being reached
     */
    void reachingServer();

    /**
     * This method is called by the client in order to notify the GUI that the server is successfully reachable
     */
    void serverReachable();

    /**
     * This method is called by the client in order to notify the GUI that the server is not reachable
     */
    void serverNotReachable();

    /**
     * This method is called by the client in order to notify the GUI that the client has successfully connected to the server
     */
    void connectedToServer();

    /**
     * This method is called by the client in order to notify the GUI that the client has disconnected from the server
     */
    void disconnectedFromServer();

    /**
     * This method is called by the client in order to notify the GUI to refresh the connected players
     * 
     * @param players
     *            A list containing the DTO representing all the connected players
     */
    void refreshPlayersList(List<PlayerDTO> players);

    /**
     * This method is called by the client in order to notify the GUI to refresh the available sessions
     * 
     * @param sessions
     *            A list containing the DTO representing all the available sessions
     */
    void refreshSessionsList(List<GameSessionDTO> sessions);

    /**
     * This method is called by the client in order to notify the GUI a request was succesfully sent to another client
     * 
     * @param request
     *            The DTO representing the sent request
     */
    void requestSent(GameRequestDTO request);

    /**
     * This method is called by the client in order to notify the GUI a request was received from another client
     * 
     * @param request
     *            The DTO representing the received request
     * @return 0 if the request was accepted, 1 otherwise
     */
    int requestReceived(GameRequestDTO request);

    /**
     * This method is called by the client in order to notify the GUI a sent request was denied by another client
     * 
     * @param request
     *            The DTO representing the received request
     */
    void requestDenied(GameRequestDTO request);

    /**
     * This method is called by the client in order to notify the GUI a session has succesfully been joined or another client has
     * joined the session
     * 
     * @param session
     *            The DTO representing the joined session
     * @param playerNb
     *            The client player number
     */
    void sessionJoined(GameSessionDTO session, int playerNb);

    /**
     * This method is called by the client in order to notify the GUI a session has been started
     * 
     * @param session
     *            The DTO representing the session
     */
    void sessionStarted(GameSessionDTO session);

    /**
     * This method is called by the client in order to notify the GUI a session has been left or another client has left a session
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
     * This method is called by the client in order to notify the GUI a session has finished
     * 
     * @param session
     *            The DTO representing the session
     */
    void sessionFinished(GameSessionDTO session);

    /**
     * This method is called by the client in order to notify the GUI to refresh the session
     * 
     * @param session
     *            The DTO representing the session
     */
    void refreshSession(GameSessionDTO session);

    /**
     * This method id called by the client in order for the GUI to display an error message to the user
     * 
     * @param message
     *            The message to display
     */
    void displayErrorMessage(String message);

}
