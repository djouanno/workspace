package com.esir.sr.sweetsnake.client;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.api.ISweetSnakeClient;
import com.esir.sr.sweetsnake.api.ISweetSnakeClientCallback;
import com.esir.sr.sweetsnake.api.ISweetSnakeIhm;
import com.esir.sr.sweetsnake.api.ISweetSnakeServer;
import com.esir.sr.sweetsnake.dto.SweetSnakeGameRequestDTO;
import com.esir.sr.sweetsnake.dto.SweetSnakeGameSessionDTO;
import com.esir.sr.sweetsnake.dto.SweetSnakePlayerDTO;
import com.esir.sr.sweetsnake.enumeration.SweetSnakeDirection;
import com.esir.sr.sweetsnake.exception.PlayerNotAvailableException;
import com.esir.sr.sweetsnake.exception.PlayerNotFoundException;
import com.esir.sr.sweetsnake.exception.UnableToConnectException;
import com.esir.sr.sweetsnake.service.SweetSnakeRmiService;

@Component
public class SweetSnakeClient implements ISweetSnakeClient
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final Logger       log = LoggerFactory.getLogger(SweetSnakeClient.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    @Autowired
    private ISweetSnakeClientCallback callback;

    @Autowired
    private SweetSnakeRmiService      rmiService;

    private ISweetSnakeServer         server;

    @Autowired
    private ISweetSnakeIhm            ihm;

    private String                    username;
    private boolean                   connected;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR & INIT
     **********************************************************************************************/

    /**
     * 
     */
    protected SweetSnakeClient() {
        super();
    }

    /**
     * 
     */
    @PostConstruct
    protected void init() {
        log.info("Initialiazing a new SweetSnakeClient");
        server = rmiService.getRmiService();
        if (server == null) {
            ihm.serverNotReachable();
        } else {
            ihm.serverReachable();
        }
    }

    /**
     * 
     */
    @PreDestroy
    protected void destroy() {
        log.info("Destroying SweetSnakeClient");
        if (connected) {
            disconnect();
        }
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeClient#reachServer()
     */
    @Override
    public void reachServer() {
        if (server == null) {
            rmiService.retryReach();
            server = rmiService.getRmiService();
            if (server == null) {
                ihm.serverNotReachable();
            } else {
                ihm.serverReachable();
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeClient#connect(java.lang.String)
     */
    @Override
    public void connect(final String _username) throws UnableToConnectException {
        if (_username == null || _username.isEmpty()) {
            throw new UnableToConnectException("invalid username");
        }
        log.debug("Connecting with username {}", _username);
        username = new String(_username);
        server.connect(callback);
        connected = true;
        ihm.successfullyConnected();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeClient#disconnect()
     */
    @Override
    public void disconnect() {
        try {
            server.disconnect(callback);
        } catch (final PlayerNotFoundException e) {
            log.error(e.getMessage(), e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeClient#requestGame(com.esir.sr.sweetsnake.dto.SweetSnakePlayerDTO)
     */
    @Override
    public void requestGame(final SweetSnakePlayerDTO player) {
        try {
            server.requestGame(callback, player);
            ihm.displayInfoMessage("Your request has been sent to " + player.getName());
        } catch (PlayerNotFoundException | PlayerNotAvailableException e) {
            ihm.displayErrorMessage(e.getMessage());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeClient#requestGame(com.esir.sr.sweetsnake.dto.SweetSnakeGameRequestDTO)
     */
    @Override
    public void requestGame(final SweetSnakeGameRequestDTO request) {
        // TODO
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeClient#startGame(com.esir.sr.sweetsnake.dto.SweetSnakeGameSessionDTO)
     */
    @Override
    public void startGame(final SweetSnakeGameSessionDTO session) {
        // TODO
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeClient#confirmMove(com.esir.sr.sweetsnake.enumeration.SweetSnakeDirection)
     */
    @Override
    public void confirmMove(final SweetSnakeDirection direction) {
        ihm.moveSnake(direction);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeClient#isConnected()
     */
    @Override
    public boolean isConnected() {
        return connected;
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeClient#getName()
     */
    @Override
    public String getUsername() {
        return username;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeClient#getScore()
     */
    @Override
    public long getScore() {
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeClient#getPlayersList()
     */
    @Override
    public List<SweetSnakePlayerDTO> getPlayersList() {
        return server.getPlayersList(callback);
    }

    /**********************************************************************************************
     * [BLOCK] SETTERS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeClient#setScore(long)
     */
    @Override
    public void setScore(final long score) {
        log.debug("New client score set to {}", score);
    }

}
