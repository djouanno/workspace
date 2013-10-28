package com.esir.sr.sweetsnake.client;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.api.IElement;
import com.esir.sr.sweetsnake.api.ISweetSnakeClient;
import com.esir.sr.sweetsnake.api.ISweetSnakeClientCallback;
import com.esir.sr.sweetsnake.api.ISweetSnakeServer;
import com.esir.sr.sweetsnake.dto.SweetSnakeGameSessionDTO;
import com.esir.sr.sweetsnake.dto.SweetSnakeGameSessionRequestDTO;
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
    private ISweetSnakeClientCallback listener;
    @Autowired
    private ISweetSnakeServer         server;

    private String                    name;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    protected SweetSnakeClient() {
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
        log.info("Initialiazing a new SweetSnakeClient");
        name = "";
    }

    @PreDestroy
    public void destroy() {
        log.info("Destroying the current SweetSnakeClient : {}", name);
        disconnect();
    }

    @Override
    public void connect() {
        try {
            server.connect(listener);
        } catch (final UnableToConnectException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void disconnect() {
        server.disconnect(listener);
    }

    @Override
    public void requestGame(final SweetSnakeGameSessionRequestDTO request) {
        // TODO
    }

    @Override
    public void startGame(final SweetSnakeGameSessionDTO session) {
        // TODO
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

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getScore() {
        return 0;
    }

    /**********************************************************************************************
     * [BLOCK] SETTERS
     **********************************************************************************************/

    @Override
    public void setName(final String _name) {
        log.debug("New client name set to {}", name);
        name = _name;
    }

    @Override
    public void setScore(final long score) {
        log.debug("New client score set to {}", score);
    }

}
