package fr.esir.project.sr.sweetsnake.server;

import java.rmi.RemoteException;

import org.slf4j.LoggerFactory;

import fr.esir.project.sr.sweetsnake.commons.api.ISweetSnakeClientCallback;
import fr.esir.project.sr.sweetsnake.commons.enumerations.Status;
import fr.esir.project.sr.sweetsnake.server.api.IPlayer;

public class Player implements IPlayer
{
    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final org.slf4j.Logger   log = LoggerFactory.getLogger(Player.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    private final ISweetSnakeClientCallback client;
    private Status                          status;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    public Player(final ISweetSnakeClientCallback _client) {
        client = _client;
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
        try {
            return client.getName();
        } catch (final RemoteException e) {
            log.error(e.getMessage(), e);
        }
        return null;
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
