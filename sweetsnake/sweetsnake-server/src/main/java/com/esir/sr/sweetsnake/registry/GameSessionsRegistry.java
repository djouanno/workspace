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
 * This class represents a game sessions registry in order to store and managed them.
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
     * Creates a new game sessions registry
     */
    protected GameSessionsRegistry() {
        super();
    }

    /**
     * Initializes a new game sessions registry
     */
    @PostConstruct
    protected void init() {
        log.info("Initializing the game sessions registry");
        sessions = new LinkedHashMap<String, GameSession>();
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * This method returns whether the registry contains or not the game session with the specified id
     * 
     * @param id
     *            The game session id to look for
     * @return True if the registry contains the game session, false otherwise
     */
    public boolean contains(final String id) {
        return sessions.containsKey(id);
    }

    /**
     * This method add a game session to the registry
     * 
     * @param session
     *            The game session to add
     */
    public void add(final GameSession session) {
        sessions.put(session.getId(), session);
        log.debug("Session {} has been added to registry", session.getId());
    }

    /**
     * This method returns the game session with the specified id from the registry
     * 
     * @param id
     *            The game session id to look for
     * @return The game session
     * @throws GameSessionNotFoundException
     *             If the game session was not found
     */
    public GameSession get(final String id) throws GameSessionNotFoundException {
        if (!contains(id)) {
            log.warn("Session with id {} was not found", id);
            throw new GameSessionNotFoundException("session not found");
        }

        return sessions.get(id);
    }

    /**
     * This method removes the game session with the specified id from the registry
     * 
     * @param id
     *            The game session id to remove
     * @throws GameSessionNotFoundException
     *             If the game session was not found
     */
    public void remove(final String id) throws GameSessionNotFoundException {
        if (!contains(id)) {
            log.warn("Session with id {} was not found", id);
            throw new GameSessionNotFoundException("session not found");
        }

        final GameSession session = sessions.get(id);
        session.destroy();
        sessions.remove(id);
        log.debug("Session {} has been removed from registry", session.getId());
    }

    /**
     * This method returns all the ids of the stored game session in the registry
     * 
     * @return A set containing the string of all the game session ids stored in the registry
     */
    public Set<String> getSessionsId() {
        return Collections.unmodifiableSet(sessions.keySet());
    }

}
