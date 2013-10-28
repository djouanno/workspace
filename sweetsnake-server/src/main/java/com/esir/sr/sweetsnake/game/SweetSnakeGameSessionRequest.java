package com.esir.sr.sweetsnake.game;

import java.io.Serializable;

import org.slf4j.LoggerFactory;

import com.esir.sr.sweetsnake.api.ISweetSnakeGameSessionRequest;
import com.esir.sr.sweetsnake.api.ISweetSnakePlayer;

public class SweetSnakeGameSessionRequest implements ISweetSnakeGameSessionRequest, Serializable
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final long             serialVersionUID = -6737578779683049874L;
    private static final org.slf4j.Logger log              = LoggerFactory.getLogger(SweetSnakeGameSessionRequest.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    private final ISweetSnakePlayer                 requestingPlayer, requestedPlayer;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    public SweetSnakeGameSessionRequest(final ISweetSnakePlayer _requestingPlayer, final ISweetSnakePlayer _requestedPlayer) {
        log.info("Initializing new game session request between {} and {}", _requestingPlayer.getName(), _requestedPlayer.getName());
        requestingPlayer = _requestingPlayer;
        requestedPlayer = _requestedPlayer;
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/



    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/



    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    @Override
    public ISweetSnakePlayer getRequestingPlayer() {
        return requestingPlayer;
    }

    @Override
    public ISweetSnakePlayer getRequestedPlayer() {
        return requestedPlayer;
    }

    /**********************************************************************************************
     * [BLOCK] SETTERS
     **********************************************************************************************/
}
