package fr.esir.project.sr.sweetsnake.client;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.esir.project.sr.sweetsnake.client.api.ISweetSnakeClient;
import fr.esir.project.sr.sweetsnake.commons.api.IElement;
import fr.esir.project.sr.sweetsnake.commons.api.ISweetSnakeClientListener;
import fr.esir.project.sr.sweetsnake.commons.api.ISweetSnakeServer;
import fr.esir.project.sr.sweetsnake.commons.exceptions.UnableToConnectException;

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
    private ISweetSnakeClientListener listener;
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
