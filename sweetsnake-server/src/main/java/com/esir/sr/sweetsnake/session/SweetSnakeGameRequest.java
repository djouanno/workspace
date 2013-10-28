package com.esir.sr.sweetsnake.session;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;

import com.esir.sr.sweetsnake.api.ISweetSnakeGameRequest;
import com.esir.sr.sweetsnake.api.ISweetSnakePlayer;
import com.esir.sr.sweetsnake.enumeration.Status;

public class SweetSnakeGameRequest implements ISweetSnakeGameRequest, Serializable
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final long             serialVersionUID = -6737578779683049874L;
    private static final org.slf4j.Logger log              = LoggerFactory.getLogger(SweetSnakeGameRequest.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    private final ISweetSnakePlayer       requestingPlayer, requestedPlayer;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     * @param _requestingPlayer
     * @param _requestedPlayer
     */
    public SweetSnakeGameRequest(final ISweetSnakePlayer _requestingPlayer, final ISweetSnakePlayer _requestedPlayer) {
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

    /**
     * 
     */
    @PostConstruct
    public void init() {
        requestingPlayer.setStatus(Status.PENDING);
        requestedPlayer.setStatus(Status.INVITED);
    }

    /**
     * 
     */
    @Override
    public void cancel() {
        requestingPlayer.setStatus(Status.AVAILABLE);
        requestedPlayer.setStatus(Status.AVAILABLE); // TODO pas sûr que ça soit
                                                     // bon ça
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    /**
     * 
     */
    @Override
    public ISweetSnakePlayer getRequestingPlayer() {
        return requestingPlayer;
    }

    /**
     * 
     */
    @Override
    public ISweetSnakePlayer getRequestedPlayer() {
        return requestedPlayer;
    }

    /**********************************************************************************************
     * [BLOCK] SETTERS
     **********************************************************************************************/



}
