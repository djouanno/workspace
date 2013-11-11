package com.esir.sr.sweetsnake.session;

import java.rmi.RemoteException;

import org.slf4j.LoggerFactory;

import com.esir.sr.sweetsnake.api.IClientCallback;
import com.esir.sr.sweetsnake.enumeration.PlayerStatus;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class Player
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The logger */
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Player.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The client callback */
    private final IClientCallback         client;

    /** The player name */
    private String                        name;

    /** The player status */
    private PlayerStatus                  status;

    /** The sent request (only one at a time) */
    private String                        sentRequestId;

    /** The received request id (only one at a time) */
    private String                        receivedRequestId;

    /** The current game session id (only one at a time) */
    private String                        gameSessionId;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     * @param _client
     */
    public Player(final IClientCallback _client) {
        client = _client;
        try {
            name = client.getUsername();
        } catch (final RemoteException e) {
            log.error("unable to retrieve client name : {}", e.getMessage(), e);
        }
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
        return name + "[status=" + status + "]";
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    /**
     * 
     * @return
     */
    public IClientCallback getClientCallback() {
        return client;
    }

    /**
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @return
     */
    public PlayerStatus getStatus() {
        return status;
    }

    /**
     * 
     * @return
     */
    public String getSentRequestId() {
        return sentRequestId;
    }

    /**
     * 
     * @return
     */
    public String getReceivedRequestId() {
        return receivedRequestId;
    }

    /**
     * 
     * @return
     */
    public String getGameSessionId() {
        return gameSessionId;
    }

    /**********************************************************************************************
     * [BLOCK] SETTERS
     **********************************************************************************************/

    /**
     * 
     * @param _status
     */
    public void setStatus(final PlayerStatus _status) {
        status = _status;
    }

    /**
     * 
     * @param _sentRequestId
     */
    public void setSentRequestId(final String _sentRequestId) {
        sentRequestId = _sentRequestId;
    }

    /**
     * 
     * @param _receivedRequestId
     */
    public void setReceivedRequestId(final String _receivedRequestId) {
        receivedRequestId = _receivedRequestId;
    }

    /**
     * 
     * @param _gameSession
     */
    public void setGameSessionId(final String _gameSessionId) {
        gameSessionId = _gameSessionId;
    }

}
