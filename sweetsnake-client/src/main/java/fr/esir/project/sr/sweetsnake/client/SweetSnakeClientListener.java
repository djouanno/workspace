package fr.esir.project.sr.sweetsnake.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.esir.project.sr.sweetsnake.client.api.ISweetSnakeClient;
import fr.esir.project.sr.sweetsnake.commons.api.IElement;
import fr.esir.project.sr.sweetsnake.commons.api.ISweetSnakeClientListener;

@Component
public class SweetSnakeClientListener extends UnicastRemoteObject implements ISweetSnakeClientListener
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

    protected SweetSnakeClientListener() throws RemoteException {
        super();
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

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
