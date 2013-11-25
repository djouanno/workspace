package com.esir.sr.sweetsnake.api;

import java.util.List;

import com.esir.sr.sweetsnake.dto.GameRequestDTO;
import com.esir.sr.sweetsnake.dto.GameSessionDTO;
import com.esir.sr.sweetsnake.dto.PlayerDTO;

/**
 * This interface represents which methods a Graphic User Interface for the server must be able to provide.<br />
 * All the methods below are intented to be called by the server according to the events it processed.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public interface IGuiForServer
{

    /**
     * This method is called by the server in order to notify the GUI that the server has been started
     */
    void serverStarted();

    /**
     * This method is called by the server in order to notify the GUI that the server has been stopped
     */
    void serverStopped();

    /**
     * This method is called by the server in order to notify the GUI to refresh the players list
     * 
     * @param players
     *            A list containing the DTO representing all the connected players
     */
    void refreshPlayers(List<PlayerDTO> players);

    /**
     * This method is called by the server in order to notify the GUI to refresh the sessions list
     * 
     * @param sessions
     *            A list containing the DTO representing all the sessions
     */
    void refreshSessions(List<GameSessionDTO> sessions);

    /**
     * This method is called by the server in order to notify the GUI to refresh the requests list
     * 
     * @param requests
     *            A list containing the DTO representing all the pending requests
     */
    void refreshRequests(List<GameRequestDTO> requests);

}
