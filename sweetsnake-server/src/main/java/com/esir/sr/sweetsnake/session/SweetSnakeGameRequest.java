package com.esir.sr.sweetsnake.session;

import java.io.Serializable;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.LoggerFactory;

import com.esir.sr.sweetsnake.api.ISweetSnakeGameRequest;
import com.esir.sr.sweetsnake.api.ISweetSnakePlayer;
import com.esir.sr.sweetsnake.constants.SweetSnakePropertiesConstants;
import com.esir.sr.sweetsnake.enumeration.SweetSnakePlayerStatus;

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

    private final String                  id;
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
        log.info("Initializing new game request between {} and {}", _requestingPlayer.getName(), _requestedPlayer.getName());
        id = RandomStringUtils.randomAlphanumeric(SweetSnakePropertiesConstants.GENERATED_ID_LENGTH);
        requestingPlayer = _requestingPlayer;
        requestedPlayer = _requestedPlayer;
        requestingPlayer.setStatus(SweetSnakePlayerStatus.INVITING);
        requestedPlayer.setStatus(SweetSnakePlayerStatus.INVITED);
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeGameRequest#cancel()
     */
    @Override
    public void cancel() {
        requestingPlayer.setStatus(SweetSnakePlayerStatus.AVAILABLE);
        requestedPlayer.setStatus(SweetSnakePlayerStatus.AVAILABLE); // TODO pas sûr que ça soit bon
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "request[id=" + id + ", requestingPlayer=" + requestingPlayer + ", requestedPlayer=" + requestedPlayer + "]";
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeGameRequest#getId()
     */
    @Override
    public String getId() {
        return id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeGameRequest#getRequestingPlayer()
     */
    @Override
    public ISweetSnakePlayer getRequestingPlayer() {
        return requestingPlayer;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeGameRequest#getRequestedPlayer()
     */
    @Override
    public ISweetSnakePlayer getRequestedPlayer() {
        return requestedPlayer;
    }

}
