package com.esir.sr.sweetsnake.session;

import java.rmi.RemoteException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.api.IClientCallback;
import com.esir.sr.sweetsnake.enumeration.PlayerStatus;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
@Component
@Scope("prototype")
public class Player
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The logger */
    private static final Logger log = LoggerFactory.getLogger(Player.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The client callback */
    private IClientCallback     callback;

    /** The player name */
    private String              name;

    /** The player status */
    private PlayerStatus        status;

    /** The sent requests */
    private List<String>        sentRequestsIds;

    /** The received request id (only one at a time) */
    private String              receivedRequestId;

    /** The current game session id (only one at a time) */
    private String              gameSessionId;

    /** The current game player's number */
    private int                 number;

    /** The current game player's snake id */
    private String              snakeId;

    /** The current game player's score */
    private int                 score;

    /** Is the player a fictive one */
    private boolean             fictive;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     * @param _client
     */
    protected Player(final IClientCallback _client) {
        callback = _client;
        sentRequestsIds = new LinkedList<String>();
        try {
            name = callback.getName();
        } catch (final RemoteException e) {
            log.error("unable to retrieve client name : {}", e.getMessage(), e);
        }
        status = PlayerStatus.AVAILABLE;
    }

    /**
     * fictive player
     * 
     * @param _name
     */
    protected Player(final String _name) {
        name = _name;
        fictive = true;
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * 
     * @param sentRequestId
     */
    public void addSentRequestId(final String sentRequestId) {
        sentRequestsIds.add(sentRequestId);
    }

    /**
     * 
     * @param sentRequestId
     */
    public void removeSentRequestId(final String sentRequestId) {
        sentRequestsIds.remove(sentRequestId);
    }

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
    public IClientCallback getCallback() {
        return callback;
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
    public List<String> getSentRequestsIds() {
        return Collections.unmodifiableList(sentRequestsIds);
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

    /**
     * 
     * @return
     */
    public int getNumber() {
        return number;
    }

    /**
     * 
     * @return
     */
    public String getSnakeId() {
        return snakeId;
    }

    /**
     * 
     * @return
     */
    public int getScore() {
        return score;
    }

    /**
     * 
     * @return
     */
    public boolean isFictive() {
        return fictive;
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

    /**
     * 
     * @param _number
     */
    public void setNumber(final int _number) {
        number = _number;
    }

    /**
     * 
     * @param _snakeId
     * @return
     */
    public void setSnakeId(final String _snakeId) {
        snakeId = _snakeId;
    }

    /**
     * 
     * @param _score
     */
    public void setScore(final int _score) {
        score = _score;
    }

}
