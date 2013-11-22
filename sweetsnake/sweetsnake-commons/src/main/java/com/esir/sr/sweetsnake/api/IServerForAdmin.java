package com.esir.sr.sweetsnake.api;

import com.esir.sr.sweetsnake.dto.GameRequestDTO;
import com.esir.sr.sweetsnake.dto.GameSessionDTO;
import com.esir.sr.sweetsnake.dto.PlayerDTO;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public interface IServerForAdmin
{

    /**
     * 
     */
    void startServer();

    /**
     * 
     */
    void stopServer();

    /**
     * 
     * @param player
     */
    void kickPlayer(PlayerDTO player);

    /**
     * 
     * @param session
     */
    void stopSession(GameSessionDTO session);

    /**
     * 
     * @param session
     */
    void removeSession(GameSessionDTO session);

    /**
     * 
     * @param request
     */
    void removeRequest(GameRequestDTO request);

}
