package com.esir.sr.sweetsnake.utils;

import java.io.Serializable;
import java.rmi.RemoteException;

import com.esir.sr.sweetsnake.api.IClientCallback;
import com.esir.sr.sweetsnake.dto.GameRequestDTO;
import com.esir.sr.sweetsnake.dto.GameSessionDTO;
import com.esir.sr.sweetsnake.dto.PlayerDTO;

public class ClientCallbackMock implements IClientCallback, Serializable
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final long serialVersionUID = 5763417596092778883L;

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    private final String      name;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    public ClientCallbackMock(final String _name) {
        name = _name;
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    @Override
    public void gameRequested(final GameRequestDTO request) {
        // TODO Auto-generated method stub
    }

    @Override
    public void gameRefused(final GameRequestDTO request) throws RemoteException {
        // TODO Auto-generated method stub
    }

    @Override
    public void gameStarted(final GameSessionDTO session) {
        // TODO Auto-generated method stub
    }

    @Override
    public void gameLeaved(final GameSessionDTO session, final PlayerDTO leaver) throws RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void refreshGame(final GameSessionDTO session) throws RemoteException {
        // TODO Auto-generated method stub

    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    @Override
    public String getUsername() throws RemoteException {
        return name;
    }

    /**********************************************************************************************
     * [BLOCK] SETTERS
     **********************************************************************************************/

    @Override
    public void setScore(final long score) throws RemoteException {
        // TODO Auto-generated method stub
    }

}
