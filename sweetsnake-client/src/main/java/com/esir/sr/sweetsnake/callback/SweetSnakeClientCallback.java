package com.esir.sr.sweetsnake.callback;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.api.ISweetSnakeClient;
import com.esir.sr.sweetsnake.api.ISweetSnakeClientCallback;
import com.esir.sr.sweetsnake.dto.SweetSnakeGameRequestDTO;
import com.esir.sr.sweetsnake.dto.SweetSnakeGameSessionDTO;
import com.esir.sr.sweetsnake.enumeration.SweetSnakeDirection;

@Component
public class SweetSnakeClientCallback extends UnicastRemoteObject implements ISweetSnakeClientCallback
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final long serialVersionUID = 2588126929388570038L;

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    @Autowired
    private ISweetSnakeClient client;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     * @throws RemoteException
     */
    protected SweetSnakeClientCallback() throws RemoteException {
        super();
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeClientCallback#requestGame(com.esir.sr.sweetsnake.dto.SweetSnakeGameRequestDTO)
     */
    @Override
    public void requestGame(final SweetSnakeGameRequestDTO request) throws RemoteException {
        client.requestGame(request);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeClientCallback#startGame(com.esir.sr.sweetsnake.dto.SweetSnakeGameSessionDTO)
     */
    @Override
    public void startGame(final SweetSnakeGameSessionDTO session) throws RemoteException {
        client.startGame(session);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.esir.sr.sweetsnake.api.ISweetSnakeClientCallback#confirmMove(com.esir.sr.sweetsnake.enumeration.SweetSnakeDirection)
     */
    @Override
    public void confirmMove(final SweetSnakeDirection direction) throws RemoteException {
        client.confirmMove(direction);
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
