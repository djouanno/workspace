package fr.esir.project.sr.sweetsnake.server;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import fr.esir.project.sr.sweetsnake.commons.Direction;
import fr.esir.project.sr.sweetsnake.commons.api.ISweetSnakeClientListener;
import fr.esir.project.sr.sweetsnake.commons.api.ISweetSnakeServer;
import fr.esir.project.sr.sweetsnake.commons.exceptions.UnableToConnectException;
import fr.esir.project.sr.sweetsnake.server.api.ISweetSnakeServerGame;

@Component
public class SweetSnakeServer implements ISweetSnakeServer
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final Logger                    log = LoggerFactory.getLogger(SweetSnakeServer.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    private Map<String, ISweetSnakeClientListener> clients;
    private List<ISweetSnakeServerGame>            games;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    protected SweetSnakeServer() {
        super();
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    @PostConstruct
    public void init() {
        log.info("Initialization of the SweetSnakeServer");
        clients = new HashMap<String, ISweetSnakeClientListener>();
    }

    @Override
    public void connect(final ISweetSnakeClientListener client) throws UnableToConnectException {
        try {
            if (client.getName() == null) {
                throw new UnableToConnectException("username cannot be null");
            }
            log.info("New client with username : {} has connected", client.getName());
            if (!clients.containsKey(client.getName())) {
                clients.put(client.getName(), client);
            } else {
                throw new UnableToConnectException("username " + client.getName() + " already taken");
            }
            log.info("Number of clients connected : {}", clients.size());
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void disconnect(final ISweetSnakeClientListener client) {
        try {
            if (clients.containsKey(client.getName())) {
                log.info("Client with username : {} has disconnected", client.getName());
                clients.remove(client.getName());
                log.info("Number of clients connected : {}", clients.size());
            }
        } catch (final RemoteException e) {
            log.error(e.getMessage(), e);
        }
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

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    /**********************************************************************************************
     * [BLOCK] SETTERS
     **********************************************************************************************/

}
