package com.esir.sr.sweetsnake.server;

import java.io.Serializable;
import java.rmi.RemoteException;

import com.esir.sr.sweetsnake.api.IElement;
import com.esir.sr.sweetsnake.api.ISweetSnakeClientCallback;
import com.esir.sr.sweetsnake.dto.SweetSnakeGameRequestDTO;

public class SweetSnakeClientCallbackMock implements ISweetSnakeClientCallback, Serializable
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

    public SweetSnakeClientCallbackMock(final String _name) {
        name = _name;
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    @Override
    public void requestGame(final SweetSnakeGameRequestDTO request) {
        // TODO Auto-generated method stub
    }

    @Override
    public void addElement(final IElement element) throws RemoteException {
        // TODO Auto-generated method stub
    }

    @Override
    public void updateElement(final IElement element) throws RemoteException {
        // TODO Auto-generated method stub
    }

    @Override
    public void removeElement(final IElement element) throws RemoteException {
        // TODO Auto-generated method stub
    }

    @Override
    public void setScore(final long score) throws RemoteException {
        // TODO Auto-generated method stub
    }

    @Override
    public String getName() throws RemoteException {
        return name;
    }

}
