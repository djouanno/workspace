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
 * This class represents a player connected to the server.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
@Component
@Scope("prototype")
public class Player implements Comparable<Player>
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The logger */
    private static final Logger   log = LoggerFactory.getLogger(Player.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The client callback */
    private final IClientCallback callback;

    /** The player name */
    private String                name;

    /** The player status */
    private PlayerStatus          status;

    /** The sent requests */
    private final List<String>    sentRequestsIds;

    /** The received request id (only one at a time) */
    private String                receivedRequestId;

    /** The current game session id (only one at a time) */
    private String                gameSessionId;

    /** The current game player's number */
    private int                   number;

    /** The current game player's snake id */
    private String                snakeId;

    /** The current game player's score */
    private int                   score;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * Creates a new player
     * 
     * @param _client
     *            The client callback
     */
    protected Player(final IClientCallback _client) {
        callback = _client;
        sentRequestsIds = new LinkedList<String>();
        try {
            name = callback.getUsername();
        } catch (final RemoteException e) {
            log.error(e.getMessage(), e);
        }
        status = PlayerStatus.AVAILABLE;
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * This method adds a sent game request id to the list of request ids sent by the player
     * 
     * @param sentRequestId
     *            The game request id to add
     */
    public void addSentRequestId(final String sentRequestId) {
        sentRequestsIds.add(sentRequestId);
    }

    /**
     * This method removes a sent game request id from the list of request ids sent by the player
     * 
     * @param sentRequestId
     *            The game request id to remove
     */
    public void removeSentRequestId(final String sentRequestId) {
        sentRequestsIds.remove(sentRequestId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(final Player otherPlayer) {
        return new Integer(number).compareTo(new Integer(otherPlayer.number));
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
     * This method returns the client callback
     * 
     * @return The client callback
     */
    public IClientCallback getCallback() {
        return callback;
    }

    /**
     * This method returns the player's name
     * 
     * @return A String representing the player's name
     */
    public String getName() {
        return name;
    }

    /**
     * This method returns the player's status
     * 
     * @return The player's status
     */
    public PlayerStatus getStatus() {
        return status;
    }

    /**
     * This method returns all the ids of the sent game requests by the player
     * 
     * @return A list containing all the string ids of the sent game requests by the player
     */
    public List<String> getSentRequestsIds() {
        return Collections.unmodifiableList(sentRequestsIds);
    }

    /**
     * This method returns the game request id of the received request (only one at a time)
     * 
     * @return A string representing the received request id if there is one, null otherwise
     */
    public String getReceivedRequestId() {
        return receivedRequestId;
    }

    /**
     * This method returns the game session if of the session the player is currently taking part in
     * 
     * @return A string representing the current game session if there is one, null otherwise
     */
    public String getGameSessionId() {
        return gameSessionId;
    }

    /**
     * This method returns the player's number in the session he is currently taking part in
     * 
     * @return An integer representing the player's number if there is one, 0 otherwise
     */
    public int getNumber() {
        return number;
    }

    /**
     * This method returns the player's snake id in the session the player is currently taking part in
     * 
     * @return A string representing the player's snake id if there is one, null otherwise
     */
    public String getSnakeId() {
        return snakeId;
    }

    /**
     * This method returns the player's score in the session he is currently taking part in
     * 
     * @return An integer representing the player's score if there is one, 0 otherwise
     */
    public int getScore() {
        return score;
    }

    /**********************************************************************************************
     * [BLOCK] SETTERS
     **********************************************************************************************/

    /**
     * This method sets the status of the player
     * 
     * @param _status
     *            The status to set
     */
    public void setStatus(final PlayerStatus _status) {
        status = _status;
    }

    /**
     * This method sets the received game request id
     * 
     * @param _receivedRequestId
     *            The received game request id
     */
    public void setReceivedRequestId(final String _receivedRequestId) {
        receivedRequestId = _receivedRequestId;
    }

    /**
     * This method sets the current game session id
     * 
     * @param _gameSession
     *            The current game session id
     */
    public void setGameSessionId(final String _gameSessionId) {
        gameSessionId = _gameSessionId;
    }

    /**
     * This method sets the player's number in the current game session
     * 
     * @param _number
     *            The player's number in the current game session
     */
    public void setNumber(final int _number) {
        number = _number;
    }

    /**
     * This method sets the player's snake id in the current game session
     * 
     * @param _number
     *            The player's snake id in the current game session
     */
    public void setSnakeId(final String _snakeId) {
        snakeId = _snakeId;
    }

    /**
     * This method sets the player's score in the current game session
     * 
     * @param _number
     *            The player's score in the current game session
     */
    public void setScore(final int _score) {
        score = _score;
    }

}
