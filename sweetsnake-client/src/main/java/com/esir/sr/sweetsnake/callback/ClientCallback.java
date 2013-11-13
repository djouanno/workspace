package com.esir.sr.sweetsnake.callback;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.api.IClient;
import com.esir.sr.sweetsnake.api.IClientCallback;
import com.esir.sr.sweetsnake.dto.GameRequestDTO;
import com.esir.sr.sweetsnake.dto.GameSessionDTO;
import com.esir.sr.sweetsnake.dto.PlayerDTO;

/**
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
    private IClient           client;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
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
     * @see com.esir.sr.sweetsnake.api.IClientCallback#refreshPlayersList(java.util.List)
     */
    @Override
    public void refreshPlayersList(final List<PlayerDTO> playersList) throws RemoteException {
        client.refreshPlayersList(playersList);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.esir.sr.sweetsnake.api.ISweetSnakeClientCallback#gameRequested(com.esir.sr.sweetsnake.dto.SweetSnakeGameRequestDTO)
     */
    @Override
    public void gameRequested(final GameRequestDTO request) throws RemoteException {
        client.gameRequested(request);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeClientCallback#gameRefused(com.esir.sr.sweetsnake.dto.SweetSnakeGameRequestDTO)
     */
    @Override
    public void gameRefused(final GameRequestDTO request) throws RemoteException {
        client.gameRefused(request);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeClientCallback#gameStarted(com.esir.sr.sweetsnake.dto.SweetSnakeGameSessionDTO)
     */
    @Override
    public void gameStarted(final GameSessionDTO session) throws RemoteException {
        client.gameStarted(session);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClientCallback#gameLeaved(com.esir.sr.sweetsnake.dto.GameSessionDTO,
     * com.esir.sr.sweetsnake.dto.PlayerDTO)
     */
    @Override
    public void gameLeaved(final GameSessionDTO session, final PlayerDTO leaver) throws RemoteException {
        client.gameLeaved(session, leaver);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClientCallback#refreshGame(com.esir.sr.sweetsnake.dto.GameSessionDTO)
     */
    @Override
    public void refreshGame(final GameSessionDTO session) throws RemoteException {
        client.refreshGame(session);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeClientCallback#getUsername()
     */
    @Override
    public String getUsername() throws RemoteException {
        return client.getUsername();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeClientCallback#setScore(long)
     */
    @Override
    public void setScore(final long score) throws RemoteException {
        client.setScore(score);
    }

}
