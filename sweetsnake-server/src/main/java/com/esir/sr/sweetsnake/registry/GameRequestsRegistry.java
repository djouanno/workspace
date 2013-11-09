package com.esir.sr.sweetsnake.registry;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.exception.GameRequestNotFoundException;
import com.esir.sr.sweetsnake.session.GameRequest;

/**
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
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(GameRequestsRegistry.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The requests map */
    private Map<String, GameRequest>      requests;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR & INIT
     **********************************************************************************************/

    /**
     * 
     */
    protected GameRequestsRegistry() {
        super();
    }

    /**
     * 
     */
    @PostConstruct
    protected void init() {
        log.info("Initializing game requests registry");
        requests = new LinkedHashMap<String, GameRequest>();
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
        return requests.containsKey(id);
    }

    /**
     * 
     * @param request
     */
    public void add(final GameRequest request) {
        requests.put(request.getId(), request);
        log.debug("Request {} has been added", requests.get(request.getId()));
    }

    /**
     * 
     * @param id
     * @return
     * @throws GameRequestNotFoundException
     */
    public GameRequest get(final String id) throws GameRequestNotFoundException {
        if (!contains(id)) {
            throw new GameRequestNotFoundException("request no more available");
        }
        return requests.get(id);
    }

    /**
     * 
     * @param id
     * @throws GameRequestNotFoundException
     */
    public void remove(final String id) throws GameRequestNotFoundException {
        if (!contains(id)) {
            throw new GameRequestNotFoundException("request no more available");
        }
        log.debug("Request {} has been removed", requests.get(id));
        requests.remove(id);
    }

    /**
     * 
     * @return
     */
    public Set<String> getRequestsId() {
        return Collections.unmodifiableSet(requests.keySet());
    }

}
