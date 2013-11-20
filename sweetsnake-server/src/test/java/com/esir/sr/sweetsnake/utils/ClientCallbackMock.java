package com.esir.sr.sweetsnake.utils;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;

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
    public void refreshPlayersList(final List<PlayerDTO> players) throws RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void requestReceived(final GameRequestDTO request) {
        // TODO Auto-generated method stub
    }

    @Override
    public void refreshSession(final GameSessionDTO session) throws RemoteException {
        // TODO Auto-generated method stub

    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    @Override
    public String getName() throws RemoteException {
        return name;
    }

    /**********************************************************************************************
     * [BLOCK] SETTERS
     **********************************************************************************************/

    @Override
    public void requestSent(final GameRequestDTO request) throws RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void sessionFinished(final GameSessionDTO session) throws RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void sessionJoined(final int playerNb, final GameSessionDTO session) throws RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void sessionStarted(final int playerNb, final GameSessionDTO session) throws RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void requestDenied(final GameRequestDTO request) throws RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void refreshSessionsList(final List<GameSessionDTO> sessions) throws RemoteException {
        // TODO Auto-generated method stub

    }

    @Override
    public void sessionLeft(final GameSessionDTO session, final PlayerDTO leaver, final boolean stopped, final boolean finished) throws RemoteException {
        // TODO Auto-generated method stub

    }

}
