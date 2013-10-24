package fr.esir.project.sr.sweetsnake.server;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import fr.esir.project.sr.sweetsnake.commons.Direction;
import fr.esir.project.sr.sweetsnake.commons.api.ISweetSnakeClientListener;
import fr.esir.project.sr.sweetsnake.commons.api.ISweetSnakeServer;

@Component
public class SweetSnakeServer implements ISweetSnakeServer
{

    private static final Logger                    log = LoggerFactory.getLogger(SweetSnakeServer.class);

    private Map<String, ISweetSnakeClientListener> clients;

    @PostConstruct
    public void init() {
        log.info("Initialization of the SweetSnakeServer");
        clients = new HashMap<String, ISweetSnakeClientListener>();
    }

    @Override
    public boolean connect(final ISweetSnakeClientListener client) {
        try {
            log.info("New client connected with name : {}", client.getName());
            log.debug("Trying to reach the client {} through RMI", client.getName());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
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
    public void move(final Direction direction) {
        // TODO Auto-generated method stub
    }

}
