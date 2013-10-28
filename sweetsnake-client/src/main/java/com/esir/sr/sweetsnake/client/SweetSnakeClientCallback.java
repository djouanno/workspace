package com.esir.sr.sweetsnake.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.api.IElement;
import com.esir.sr.sweetsnake.api.ISweetSnakeClient;
import com.esir.sr.sweetsnake.api.ISweetSnakeClientCallback;
import com.esir.sr.sweetsnake.dto.SweetSnakeGameRequestDTO;

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

    protected SweetSnakeClientCallback() throws RemoteException {
        super();
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    @Override
    public void requestGame(final SweetSnakeGameRequestDTO request) {
        client.requestGame(request);
    }

    @Override
    public void addElement(final IElement element) throws RemoteException {
        client.addElement(element);
    }

    @Override
    public void updateElement(final IElement element) throws RemoteException {
        client.updateElement(element);
    }

    @Override
    public void removeElement(final IElement element) throws RemoteException {
        client.removeElement(element);
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    @Override
    public String getName() throws RemoteException {
        return client.getName();
    }

    /**********************************************************************************************
     * [BLOCK] SETTERS
     **********************************************************************************************/

    @Override
    public void setScore(final long score) throws RemoteException {
        client.setScore(score);
    }

}
