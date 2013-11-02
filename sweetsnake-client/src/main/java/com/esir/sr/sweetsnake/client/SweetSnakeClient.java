package com.esir.sr.sweetsnake.client;

import java.util.LinkedList;
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
import com.esir.sr.sweetsnake.exception.PlayerNotFoundException;
import com.esir.sr.sweetsnake.exception.UnableToConnectException;

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
    private ISweetSnakeServer         server;

    @Autowired
    private ISweetSnakeIhm            ihm;

    private String                    username;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     */
    protected SweetSnakeClient() {
        super();
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * 
     */
    @PostConstruct
    public void init() {
        log.info("Initialiazing a new SweetSnakeClient");
    }

    /**
     * 
     */
    @PreDestroy
    public void destroy() {
        log.info("Destroying the current SweetSnakeClient : {}", username);
        disconnect();
    }

    /**
     * @throws UnableToConnectException
     * 
     */
    @Override
    public void connect(final String _username) throws UnableToConnectException {
        if (_username == null || _username.isEmpty()) {
            throw new UnableToConnectException("invalid username");
        }
        log.debug("Connecting with username {}", _username);
        username = new String(_username);
        try {
            server.connect(callback);
            ihm.successfullyConnected();
        } catch (final UnableToConnectException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 
     */
    @Override
    public void disconnect() {
        try {
            server.disconnect(callback);
        } catch (final PlayerNotFoundException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 
     */
    @Override
    public void requestGame(final SweetSnakeGameRequestDTO request) {
        // TODO
    }

    /**
     * 
     */
    @Override
    public void startGame(final SweetSnakeGameSessionDTO session) {
        // TODO
    }

    /**
     * 
     */
    @Override
    public void confirmMove(final SweetSnakeDirection direction) {
        ihm.moveSnake(direction);
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    /**
     * 
     */
    @Override
    public String getName() {
        return username;
    }

    /**
     * 
     */
    @Override
    public long getScore() {
        return 0;
    }

    @Override
    public List<String> getPlayersList() {
        final List<String> players = new LinkedList<String>();
        final List<SweetSnakePlayerDTO> playersDTO = server.getPlayersList(callback);

        for (final SweetSnakePlayerDTO playerDTO : playersDTO) {
            players.add(new String(playerDTO.getName() + " [" + playerDTO.getStatus() + "]"));
        }

        return players;
    }

    /**********************************************************************************************
     * [BLOCK] SETTERS
     **********************************************************************************************/

    /**
     * 
     */
    @Override
    public void setScore(final long score) {
        log.debug("New client score set to {}", score);
    }

}
