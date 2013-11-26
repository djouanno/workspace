package com.esir.sr.sweetsnake.registry;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.exception.GameRequestNotFoundException;
import com.esir.sr.sweetsnake.session.GameRequest;

/**
 * This class represents a game requests registry in order to store and managed them.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
@Component
public class GameRequestsRegistry
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The logger */
    private static final Logger      log = LoggerFactory.getLogger(GameRequestsRegistry.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The requests map */
    private Map<String, GameRequest> requests;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR & INIT
     **********************************************************************************************/

    /**
     * Creates a new game requests registry
     */
    protected GameRequestsRegistry() {
        super();
    }

    /**
     * Initializes a new game requests registry
     */
    @PostConstruct
    protected void init() {
        log.info("Initializing the game requests registry");
        requests = new LinkedHashMap<String, GameRequest>();
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * This method returns whether the registry contains or not the game request with the specified id
     * 
     * @param id
     *            The game request id to look for
     * @return True if the registry contains the game request, false otherwise
     */
    public boolean contains(final String id) {
        return requests.containsKey(id);
    }

    /**
     * This method add a game request to the registry
     * 
     * @param request
     *            The game request to add
     */
    public void add(final GameRequest request) {
        requests.put(request.getId(), request);
        log.debug("Request {} has been added to registry", request.getId());
    }

    /**
     * This method returns the game request with the specified id from the registry
     * 
     * @param id
     *            The game request id to look for
     * @return The game request
     * @throws GameRequestNotFoundException
     *             If the game request was not found
     */
    public GameRequest get(final String id) throws GameRequestNotFoundException {
        if (!contains(id)) {
            log.warn("Request with id {} is no more available", id);
            throw new GameRequestNotFoundException("request no more available");
        }

        return requests.get(id);
    }

    /**
     * This method removes the game request with the specified id from the registry
     * 
     * @param id
     *            The game request id to remove
     * @throws GameRequestNotFoundException
     *             If the game request was not found
     */
    public void remove(final String id) throws GameRequestNotFoundException {
        if (!contains(id)) {
            log.warn("Request with id {} is no more available", id);
            throw new GameRequestNotFoundException("request no more available");
        }

        final GameRequest request = requests.get(id);
        request.destroy();
        requests.remove(id);
        log.debug("Request {} has been removed from registry", request.getId());
    }

    /**
     * This method returns all the ids of the stored game requests in the registry
     * 
     * @return A set containing the string of all the game requests ids stored in the registry
     */
    public Set<String> getRequestsId() {
        return Collections.unmodifiableSet(requests.keySet());
    }

}
