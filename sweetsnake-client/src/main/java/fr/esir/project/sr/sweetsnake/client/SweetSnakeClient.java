package fr.esir.project.sr.sweetsnake.client;

import java.rmi.RemoteException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.esir.project.sr.sweetsnake.client.api.ISweetSnakeClient;
import fr.esir.project.sr.sweetsnake.commons.api.IElement;
import fr.esir.project.sr.sweetsnake.commons.api.ISweetSnakeClientListener;
import fr.esir.project.sr.sweetsnake.commons.api.ISweetSnakeServer;

@Component
public class SweetSnakeClient implements ISweetSnakeClient
{

    @Autowired
    private ISweetSnakeClientListener listener;

    @Autowired
    private ISweetSnakeServer         server;

    protected SweetSnakeClient() throws RemoteException {
        super();
    }

    @PostConstruct
    public void init() throws RemoteException {
        System.out.println("POUET POUET");
        server.connect(listener);
    }

    @Override
    public long getScore() {
        return 0;
    }

    @Override
    public void addElement(IElement element) {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateElement(IElement element) {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeElement(IElement element) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setScore(long score) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getName() {
        return "toto";
    }
}
