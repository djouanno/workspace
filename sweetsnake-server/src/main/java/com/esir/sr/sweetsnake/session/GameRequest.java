package com.esir.sr.sweetsnake.session;

import java.io.Serializable;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.LoggerFactory;

import com.esir.sr.sweetsnake.api.IGameRequest;
import com.esir.sr.sweetsnake.api.IPlayer;
import com.esir.sr.sweetsnake.constants.PropertiesConstants;
import com.esir.sr.sweetsnake.enumeration.PlayerStatus;

public class GameRequest implements IGameRequest, Serializable
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final long             serialVersionUID = -6737578779683049874L;
    private static final org.slf4j.Logger log              = LoggerFactory.getLogger(GameRequest.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    private final String                  id;
    private final IPlayer       requestingPlayer, requestedPlayer;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     * @param _requestingPlayer
     * @param _requestedPlayer
     */
    public GameRequest(final IPlayer _requestingPlayer, final IPlayer _requestedPlayer) {
        log.info("Initializing new game request between {} and {}", _requestingPlayer.getName(), _requestedPlayer.getName());
        id = RandomStringUtils.randomAlphanumeric(PropertiesConstants.GENERATED_ID_LENGTH);
        requestingPlayer = _requestingPlayer;
        requestedPlayer = _requestedPlayer;
        requestingPlayer.setStatus(PlayerStatus.INVITING);
        requestedPlayer.setStatus(PlayerStatus.INVITED);
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
        requestingPlayer.setStatus(PlayerStatus.AVAILABLE);
        requestedPlayer.setStatus(PlayerStatus.AVAILABLE); // TODO pas sûr que ça soit bon
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
    public IPlayer getRequestingPlayer() {
        return requestingPlayer;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeGameRequest#getRequestedPlayer()
     */
    @Override
    public IPlayer getRequestedPlayer() {
        return requestedPlayer;
    }

}
