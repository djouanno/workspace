package com.esir.sr.sweetsnake.api;

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
public interface IServerGui
{

    /**
     * 
     */
    void serverStarted();

    /**
     * 
     */
    void serverStopped();

    /**
     * 
     * @param players
     */
    void refreshPlayers(List<PlayerDTO> players);

    /**
     * 
     * @param sessions
     */
    void refreshSessions(List<GameSessionDTO> sessions);

    /**
     * 
     * @param requests
     */
    void refreshRequests(List<GameRequestDTO> requests);

}
