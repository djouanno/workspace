package com.esir.sr.sweetsnake.session;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.LoggerFactory;

import com.esir.sr.sweetsnake.constants.PropertiesConstants;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class GameRequest
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The logger */
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(GameRequest.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The request id */
    private final String                  id;

    /** The requesting player */
    private final Player                  requestingPlayer;

    /** The requested player */
    private final Player                  requestedPlayer;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     * @param _requestingPlayer
     * @param _requestedPlayer
     */
    public GameRequest(final Player _requestingPlayer, final Player _requestedPlayer) {
        log.info("Initializing new game request between {} and {}", _requestingPlayer.getName(), _requestedPlayer.getName());
        id = RandomStringUtils.randomAlphanumeric(PropertiesConstants.GENERATED_ID_LENGTH);
        requestingPlayer = _requestingPlayer;
        requestedPlayer = _requestedPlayer;
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

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

    /**
     * 
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @return
     */
    public Player getRequestingPlayer() {
        return requestingPlayer;
    }

    /**
     * 
     * @return
     */
    public Player getRequestedPlayer() {
        return requestedPlayer;
    }

}
