package com.esir.sr.sweetsnake.registry;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.exception.GameSessionNotFoundException;
import com.esir.sr.sweetsnake.session.GameSession;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
@Component
public class GameSessionsRegistry
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The logger */
    private static final Logger      log = LoggerFactory.getLogger(GameSessionsRegistry.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The sessions map */
    private Map<String, GameSession> sessions;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     */
    protected GameSessionsRegistry() {
        super();
    }

    /**
     * 
     */
    @PostConstruct
    protected void init() {
        log.info("Initializing game sessions registry");
        sessions = new LinkedHashMap<String, GameSession>();
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * 
     * @param id
     * @return
     */
    public boolean contains(final String id) {
        return sessions.containsKey(id);
    }

    /**
     * 
     * @param session
     */
    public void add(final GameSession session) {
        sessions.put(session.getId(), session);
        log.debug("Session {} has been added to registry", session.getId());
    }

    /**
     * 
     * @param id
     * @return
     * @throws GameSessionNotFoundException
     */
    public GameSession get(final String id) throws GameSessionNotFoundException {
        if (!contains(id)) {
            throw new GameSessionNotFoundException("session not found");
        }
        return sessions.get(id);
    }

    /**
     * 
     * @param id
     * @throws GameSessionNotFoundException
     */
    public void remove(final String id) throws GameSessionNotFoundException {
        if (!contains(id)) {
            throw new GameSessionNotFoundException("session not found");
        }
        final GameSession session = sessions.get(id);
        session.destroy();
        sessions.remove(id);
        log.debug("Session {} has been removed from registry", session.getId());
    }

    /**
     * 
     * @return
     */
    public Set<String> getSessionsId() {
        return Collections.unmodifiableSet(sessions.keySet());
    }

}
