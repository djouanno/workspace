package fr.esir.project.sr.sweetsnake.server;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import fr.esir.project.sr.sweetsnake.commons.Direction;
import fr.esir.project.sr.sweetsnake.commons.api.ISweetSnakeClientListener;
import fr.esir.project.sr.sweetsnake.commons.api.ISweetSnakeServer;

@Component
public class SweetSnakeServer implements ISweetSnakeServer
{

    private Map<String, ISweetSnakeClientListener> clients;

    @PostConstruct
    public void init() {
        clients = new HashMap<String, ISweetSnakeClientListener>();
    }

    @Override
    public boolean connect(ISweetSnakeClientListener client) {
        try {
            System.out.println(client.getName());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        // clients.put(listener.getName(), listener);
        // System.out.println("New client connected [" + clients.size() +
        // "] : " + listener.getName());
        return true;
    }

    @Override
    public void disconnect() {
        // TODO Auto-generated method stub

    }

    @Override
    public void startGame() {
        // TODO Auto-generated method stub

    }

    @Override
    public void exitGame() {
        // TODO Auto-generated method stub

    }

    @Override
    public void move(Direction direction) {
        // TODO Auto-generated method stub

    }

}
