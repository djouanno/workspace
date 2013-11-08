package com.esir.sr.sweetsnake.factory;

import com.esir.sr.sweetsnake.api.IGameRequest;
import com.esir.sr.sweetsnake.api.IGameSession;
import com.esir.sr.sweetsnake.api.IPlayer;
import com.esir.sr.sweetsnake.dto.GameRequestDTO;
import com.esir.sr.sweetsnake.dto.GameSessionDTO;
import com.esir.sr.sweetsnake.dto.PlayerDTO;

public class SessionsFactory
{

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * 
     * @param request
     * @return
     */
    public static GameRequestDTO convertGameSessionRequest(final IGameRequest request) {
        final GameRequestDTO requestDTO = new GameRequestDTO(request.getId(), request.getRequestingPlayer().getName(), request.getRequestedPlayer().getName());
        return requestDTO;
    }

    /**
     * 
     * @param session
     * @return
     */
    public static GameSessionDTO convertGameSession(final IGameSession session) {
        final GameSessionDTO sessionDTO = new GameSessionDTO(session.getId(), session.getPlayer1().getName(), session.getPlayer2().getName());
        return sessionDTO;
    }

    /**
     * 
     * @param player
     * @return
     */
    public static PlayerDTO convertPlayer(final IPlayer player) {
        final PlayerDTO playerDTO = new PlayerDTO(player.getName(), player.getStatus());
        return playerDTO;
    }

}
