package com.esir.sr.sweetsnake.callback;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.api.IClientCallback;
import com.esir.sr.sweetsnake.api.IClientForServer;
import com.esir.sr.sweetsnake.dto.GameRequestDTO;
import com.esir.sr.sweetsnake.dto.GameSessionDTO;
import com.esir.sr.sweetsnake.dto.PlayerDTO;

/**
 * This class implements the client callback interface defined in the API
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
@Component
public class ClientCallback extends UnicastRemoteObject implements IClientCallback
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long serialVersionUID = 2588126929388570038L;

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The client */
    @Autowired
    private IClientForServer  client;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * Initializes a new client callback
     * 
     * @throws RemoteException
     */
    protected ClientCallback() throws RemoteException {
        super();
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClientCallback#connected()
     */
    @Override
    public void connected() throws RemoteException {
        client.connected();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClientCallback#disconnected()
     */
    @Override
    public void disconnected() throws RemoteException {
        client.disconnected();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClientCallback#refreshPlayersList(java.util.List)
     */
    @Override
    public void refreshPlayersList(final List<PlayerDTO> playersList) throws RemoteException {
        client.refreshPlayersList(playersList);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClientCallback#refreshSessionsList(java.util.List)
     */
    @Override
    public void refreshSessionsList(final List<GameSessionDTO> sessionsList) throws RemoteException {
        client.refreshSessionsList(sessionsList);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClientCallback#requestSent(com.esir.sr.sweetsnake.dto.GameRequestDTO)
     */
    @Override
    public void requestSent(final GameRequestDTO request) throws RemoteException {
        client.requestSent(request);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClientCallback#requestReceived(com.esir.sr.sweetsnake.dto.GameRequestDTO)
     */
    @Override
    public void requestReceived(final GameRequestDTO request) throws RemoteException {
        client.requestReceived(request);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClientCallback#requestDenied(com.esir.sr.sweetsnake.dto.GameRequestDTO)
     */
    @Override
    public void requestDenied(final GameRequestDTO request) throws RemoteException {
        client.requestDenied(request);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClientCallback#sessionJoined(int, com.esir.sr.sweetsnake.dto.GameSessionDTO)
     */
    @Override
    public void sessionJoined(final int playerNb, final GameSessionDTO session) throws RemoteException {
        client.sessionJoined(playerNb, session);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClientCallback#sessionStarted(com.esir.sr.sweetsnake.dto.GameSessionDTO)
     */
    @Override
    public void sessionStarted(final GameSessionDTO session) throws RemoteException {
        client.sessionStarted(session);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClientCallback#sessionLeft(com.esir.sr.sweetsnake.dto.GameSessionDTO,
     * com.esir.sr.sweetsnake.dto.PlayerDTO, boolean, boolean)
     */
    @Override
    public void sessionLeft(final GameSessionDTO session, final PlayerDTO leaver, final boolean stopped, final boolean finished) throws RemoteException {
        client.sessionLeft(session, leaver, stopped, finished);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClientCallback#sessionFinished(com.esir.sr.sweetsnake.dto.GameSessionDTO)
     */
    @Override
    public void sessionFinished(final GameSessionDTO session) throws RemoteException {
        client.sessionFinished(session);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClientCallback#refreshSession(com.esir.sr.sweetsnake.dto.GameSessionDTO)
     */
    @Override
    public void refreshSession(final GameSessionDTO session) throws RemoteException {
        client.refreshSession(session);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClientCallback#getUsername()
     */
    @Override
    public String getUsername() throws RemoteException {
        return client.getUsername();
    }

}
