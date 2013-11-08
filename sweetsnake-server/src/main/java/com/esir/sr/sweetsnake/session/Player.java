package com.esir.sr.sweetsnake.session;

import java.rmi.RemoteException;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.LoggerFactory;

import com.esir.sr.sweetsnake.api.IClientCallback;
import com.esir.sr.sweetsnake.api.IPlayer;
import com.esir.sr.sweetsnake.enumeration.PlayerStatus;

public class Player implements IPlayer
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final org.slf4j.Logger   log = LoggerFactory.getLogger(Player.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    private final IClientCallback client;
    private String                          name;
    private PlayerStatus          status;
    private Set<String>                     sentRequestsIds, receivedRequestsIds;

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
            sentRequestsIds = new TreeSet<String>();
            receivedRequestsIds = new TreeSet<String>();
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
     * @see com.esir.sr.sweetsnake.api.ISweetSnakePlayer#addSentRequestId(java.lang.String)
     */
    @Override
    public void addSentRequestId(final String requestId) {
        sentRequestsIds.add(requestId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakePlayer#addReceivedRequestId(java.lang.String)
     */
    @Override
    public void addReceivedRequestId(final String requestId) {
        receivedRequestsIds.add(requestId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakePlayer#removeSentRequestId(java.lang.String)
     */
    @Override
    public void removeSentRequestId(final String requestId) {
        sentRequestsIds.remove(requestId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakePlayer#removeReceivedRequestId(java.lang.String)
     */
    @Override
    public void removeReceivedRequestId(final String requestId) {
        receivedRequestsIds.remove(requestId);
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

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakePlayer#getClientCallback()
     */
    @Override
    public IClientCallback getClientCallback() {
        return client;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakePlayer#getName()
     */
    @Override
    public String getName() {
        return name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakePlayer#getStatus()
     */
    @Override
    public PlayerStatus getStatus() {
        return status;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakePlayer#getSentRequestsIds()
     */
    @Override
    public Set<String> getSentRequestsIds() {
        return Collections.unmodifiableSet(sentRequestsIds);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakePlayer#getReceivedRequestsIds()
     */
    @Override
    public Set<String> getReceivedRequestsIds() {
        return Collections.unmodifiableSet(receivedRequestsIds);
    }

    /**********************************************************************************************
     * [BLOCK] SETTERS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakePlayer#setStatus(com.esir.sr.sweetsnake.enumeration .Status)
     */
    @Override
    public void setStatus(final PlayerStatus _status) {
        status = _status;
    }

}
