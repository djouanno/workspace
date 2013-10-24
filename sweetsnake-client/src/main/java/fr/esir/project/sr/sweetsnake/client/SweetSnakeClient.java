package fr.esir.project.sr.sweetsnake.client;

import java.rmi.RemoteException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.esir.project.sr.sweetsnake.client.api.ISweetSnakeClient;
import fr.esir.project.sr.sweetsnake.commons.api.IElement;
import fr.esir.project.sr.sweetsnake.commons.api.ISweetSnakeClientListener;
import fr.esir.project.sr.sweetsnake.commons.api.ISweetSnakeServer;

@Component
public class SweetSnakeClient implements ISweetSnakeClient
{

    private static final Logger       log = LoggerFactory.getLogger(SweetSnakeClient.class);

    @Autowired
    private ISweetSnakeClientListener listener;

    @Autowired
    private ISweetSnakeServer         server;

    protected SweetSnakeClient() throws RemoteException {
        super();
    }

    @PostConstruct
    public void init() throws RemoteException {
        log.info("Initialiazing a new SweetSnakeClient");
        server.connect(listener);
    }

    @Override
    public long getScore() {
        return 0;
    }

    @Override
    public void addElement(final IElement element) {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateElement(final IElement element) {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeElement(final IElement element) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setScore(final long score) {
        log.debug("New client score set to {}", score);
    }

    @Override
    public String getName() {
        return "toto";
    }
}
