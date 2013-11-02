package com.esir.sr.sweetsnake.session;

import java.rmi.RemoteException;

import org.slf4j.LoggerFactory;

import com.esir.sr.sweetsnake.api.ISweetSnakeClientCallback;
import com.esir.sr.sweetsnake.api.ISweetSnakePlayer;
import com.esir.sr.sweetsnake.enumeration.SweetSnakePlayerStatus;

public class SweetSnakePlayer implements ISweetSnakePlayer
{
    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final org.slf4j.Logger   log = LoggerFactory.getLogger(SweetSnakePlayer.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    private final ISweetSnakeClientCallback client;
    private String                          name;
    private SweetSnakePlayerStatus                          status;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     * @param _client
     */
    public SweetSnakePlayer(final ISweetSnakeClientCallback _client) {
        client = _client;
        try {
            name = client.getName();
        } catch (final RemoteException e) {
            log.error("unable to retrieve client name : {}", e.getMessage(), e);
        }
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/



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

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakePlayer#getClientCallback()
     */
    @Override
    public ISweetSnakeClientCallback getClientCallback() {
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

    /**
     * 
     */
    @Override
    public SweetSnakePlayerStatus getStatus() {
        return status;
    }

    /**********************************************************************************************
     * [BLOCK] SETTERS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.esir.sr.sweetsnake.api.ISweetSnakePlayer#setStatus(com.esir.sr.sweetsnake.enumeration
     * .Status)
     */
    @Override
    public void setStatus(final SweetSnakePlayerStatus _status) {
        status = _status;
    }

}
