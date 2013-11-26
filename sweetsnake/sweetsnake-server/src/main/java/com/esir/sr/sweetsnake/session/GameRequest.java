package com.esir.sr.sweetsnake.session;

import java.rmi.RemoteException;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.constants.PropertiesConstants;
import com.esir.sr.sweetsnake.dto.GameRequestDTO;
import com.esir.sr.sweetsnake.enumeration.PlayerStatus;
import com.esir.sr.sweetsnake.factory.DtoConverterFactory;

/**
 * This class represents a game request sent by a player to another one in order to invite him to join a game session.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
@Component
@Scope("prototype")
public class GameRequest extends AbstractSession
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The logger */
    private static final Logger log = LoggerFactory.getLogger(GameRequest.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The request id */
    private final String        id;

    /** The session id */
    private final String        sessionId;

    /** The requesting player */
    private final Player        requestingPlayer;

    /** The requested player */
    private final Player        requestedPlayer;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR & INIT
     **********************************************************************************************/

    /**
     * Creates a new game request
     * 
     * @param _requestingPlayer
     *            The requesting player
     * @param _requestedPlayer
     *            The requested player
     */
    protected GameRequest(final String _sessionId, final Player _requestingPlayer, final Player _requestedPlayer) {
        super();
        id = RandomStringUtils.randomAlphanumeric(PropertiesConstants.GENERATED_ID_LENGTH);
        sessionId = _sessionId;
        requestingPlayer = _requestingPlayer;
        requestedPlayer = _requestedPlayer;
    }

    /**
     * Initializes a new game request
     */
    @PostConstruct
    protected void init() {
        log.debug("Initializing a new game request between {} and {}", requestingPlayer.getName(), requestedPlayer.getName());

        requestingPlayer.addSentRequestId(id);
        requestedPlayer.setReceivedRequestId(id);
        requestedPlayer.setStatus(PlayerStatus.INVITED);

        final GameRequestDTO requestDTO = DtoConverterFactory.convertGameRequest(this);

        try {
            requestingPlayer.getCallback().requestSent(requestDTO);
        } catch (final RemoteException e) {
            log.error(e.getMessage(), e);
        }

        // requestReceived() on client side is a blocking method while the other player has not answered
        // so we have to launch it from a new thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    requestedPlayer.getCallback().requestReceived(requestDTO);
                } catch (final RemoteException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }).start();
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * This method cancels the game request
     */
    public void cancel() {
        // TODO
    }

    /**
     * This method denies a game request
     */
    public void deny() {
        try {
            requestedPlayer.setStatus(PlayerStatus.AVAILABLE);
            requestingPlayer.getCallback().requestDenied(DtoConverterFactory.convertGameRequest(this));
        } catch (final RemoteException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * This method destroys a game request
     */
    public void destroy() {
        requestingPlayer.removeSentRequestId(id);
        requestedPlayer.setReceivedRequestId(null);
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

    /**
     * This method returns the game request id
     * 
     * @return A string representing the game request id
     */
    public String getId() {
        return id;
    }

    /**
     * This method returns the game session id from which the request was sent
     * 
     * @return A string representing the game session id from which the request was sent
     */
    public String getSessionid() {
        return sessionId;
    }

    /**
     * This method returns the requesting player
     * 
     * @return The requesting player
     */
    public Player getRequestingPlayer() {
        return requestingPlayer;
    }

    /**
     * This method returns the requested player
     * 
     * @return The requested player
     */
    public Player getRequestedPlayer() {
        return requestedPlayer;
    }

}
