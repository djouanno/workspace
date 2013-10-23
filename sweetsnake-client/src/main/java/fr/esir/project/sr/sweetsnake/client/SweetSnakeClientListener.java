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

    private static final long serialVersionUID = 2588126929388570038L;

    @Autowired
    private ISweetSnakeClient client;

    protected SweetSnakeClientListener() throws RemoteException {
        super();
    }

    @Override
    public void addElement(IElement element) throws RemoteException {
        client.addElement(element);
    }

    @Override
    public void updateElement(IElement element) throws RemoteException {
        client.updateElement(element);
    }

    @Override
    public void removeElement(IElement element) throws RemoteException {
        client.removeElement(element);
    }

    @Override
    public void setScore(long score) throws RemoteException {
        client.setScore(score);
    }

    @Override
    public String getName() throws RemoteException {
        return client.getName();
    }

}
