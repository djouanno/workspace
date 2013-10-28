package com.esir.sr.sweetsnake.game;

import java.rmi.RemoteException;

import org.slf4j.LoggerFactory;

import com.esir.sr.sweetsnake.api.ISweetSnakeClientCallback;
import com.esir.sr.sweetsnake.api.ISweetSnakePlayer;
import com.esir.sr.sweetsnake.enumeration.Status;

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
    private Status                          status;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    public SweetSnakePlayer(final ISweetSnakeClientCallback _client) {
        client = _client;
        try {
            name = client.getName();
        } catch (final RemoteException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/



    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/



    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    @Override
    public ISweetSnakeClientCallback getClientCallback() {
        return client;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    /**********************************************************************************************
     * [BLOCK] SETTERS
     **********************************************************************************************/

    @Override
    public void setStatus(final Status _status) {
        status = _status;
    }

}
