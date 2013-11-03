package com.esir.sr.sweetsnake.registry;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.api.ISweetSnakeGameSession;
import com.esir.sr.sweetsnake.api.ISweetSnakeGameSessionsRegistry;
import com.esir.sr.sweetsnake.exception.GameSessionNotFoundException;

@Component
public class SweetSnakeGameSessionsRegistry implements ISweetSnakeGameSessionsRegistry
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final org.slf4j.Logger       log = LoggerFactory.getLogger(SweetSnakeGameSessionsRegistry.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    private Map<String, ISweetSnakeGameSession> sessions;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     */
    protected SweetSnakeGameSessionsRegistry() {
        super();
    }

    /**
     * 
     */
    @PostConstruct
    protected void init() {
        log.info("Initializing game sessions registry");
        sessions = new LinkedHashMap<String, ISweetSnakeGameSession>();
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeGameSessionsRegistry#contains(java.lang.String)
     */
    @Override
    public boolean contains(final String id) {
        return sessions.containsKey(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeGameSessionsRegistry#add(com.esir.sr.sweetsnake.api .ISweetSnakeGameSession)
     */
    @Override
    public void add(final ISweetSnakeGameSession session) {
        sessions.put(session.getId(), session);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeGameSessionsRegistry#get(java.lang.String)
     */
    @Override
    public ISweetSnakeGameSession get(final String id) throws GameSessionNotFoundException {
        if (!contains(id)) {
            throw new GameSessionNotFoundException("session not found");
        }
        return sessions.get(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeGameSessionsRegistry#remove(java.lang.String)
     */
    @Override
    public void remove(final String id) throws GameSessionNotFoundException {
        if (!contains(id)) {
            throw new GameSessionNotFoundException("session not found");
        }
        sessions.remove(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeGameSessionsRegistry#getSessionsId()
     */
    @Override
    public List<String> getSessionsId() {
        final List<String> ids = new LinkedList<String>();
        for (final String id : sessions.keySet()) {
            ids.add(id);
        }

        return ids;
    }

}
