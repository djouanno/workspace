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
     * 
     * @param _requestingPlayer
     * @param _requestedPlayer
     */
    protected GameRequest(final String _sessionId, final Player _requestingPlayer, final Player _requestedPlayer) {
        super();
        id = RandomStringUtils.randomAlphanumeric(PropertiesConstants.GENERATED_ID_LENGTH);
        sessionId = _sessionId;
        requestingPlayer = _requestingPlayer;
        requestedPlayer = _requestedPlayer;
    }

    /**
     * 
     */
    @PostConstruct
    protected void init() {
        log.info("Initializing new game request between {} and {}", requestingPlayer.getName(), requestedPlayer.getName());

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
     * 
     */
    public void cancel() {

    }

    /**
     * 
     * @param allDenied
     */
    public void deny(final boolean allDenied) {
        try {
            requestedPlayer.setStatus(PlayerStatus.AVAILABLE);
            if (allDenied) {
                requestingPlayer.setStatus(PlayerStatus.AVAILABLE);
            }
            requestingPlayer.getCallback().requestDenied(allDenied, DtoConverterFactory.convertGameRequest(this));
        } catch (final RemoteException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 
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
    public String getSessionid() {
        return sessionId;
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
