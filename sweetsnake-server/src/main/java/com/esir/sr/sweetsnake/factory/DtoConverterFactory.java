package com.esir.sr.sweetsnake.factory;

import com.esir.sr.sweetsnake.dto.GameRequestDTO;
import com.esir.sr.sweetsnake.dto.GameSessionDTO;
import com.esir.sr.sweetsnake.dto.PlayerDTO;
import com.esir.sr.sweetsnake.session.GameRequest;
import com.esir.sr.sweetsnake.session.GameSession;
import com.esir.sr.sweetsnake.session.Player;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class DtoConverterFactory
{

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * 
     * @param request
     * @return
     */
    public static GameRequestDTO convertGameSessionRequest(final GameRequest request) {
        final GameRequestDTO requestDTO = new GameRequestDTO(request.getId(), request.getRequestingPlayer().getName(), request.getRequestedPlayer().getName());
        return requestDTO;
    }

    /**
     * 
     * @param session
     * @return
     */
    public static GameSessionDTO convertGameSession(final GameSession session) {
        final GameSessionDTO sessionDTO = new GameSessionDTO(session.getId(), session.getPlayer1().getName(), session.getPlayer2().getName());
        return sessionDTO;
    }

    /**
     * 
     * @param player
     * @return
     */
    public static PlayerDTO convertPlayer(final Player player) {
        final PlayerDTO playerDTO = new PlayerDTO(player.getName(), player.getStatus());
        return playerDTO;
    }

}
