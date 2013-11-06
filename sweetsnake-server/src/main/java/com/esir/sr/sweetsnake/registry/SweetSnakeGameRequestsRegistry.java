package com.esir.sr.sweetsnake.registry;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.api.ISweetSnakeGameRequest;
import com.esir.sr.sweetsnake.api.ISweetSnakeGameRequestsRegistry;
import com.esir.sr.sweetsnake.exception.GameRequestNotFoundException;

@Component
public class SweetSnakeGameRequestsRegistry implements ISweetSnakeGameRequestsRegistry
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final org.slf4j.Logger       log = LoggerFactory.getLogger(SweetSnakeGameRequestsRegistry.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    private Map<String, ISweetSnakeGameRequest> requests;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR & INIT
     **********************************************************************************************/

    /**
     * 
     */
    protected SweetSnakeGameRequestsRegistry() {
        super();
    }

    /**
     * 
     */
    @PostConstruct
    protected void init() {
        log.info("Initializing game requests registry");
        requests = new LinkedHashMap<String, ISweetSnakeGameRequest>();
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeGameRequestsRegistry#contains(java.lang.String)
     */
    @Override
    public boolean contains(final String id) {
        return requests.containsKey(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeGameRequestsRegistry#add(com.esir.sr.sweetsnake.api .ISweetSnakeGameRequest)
     */
    @Override
    public void add(final ISweetSnakeGameRequest request) {
        requests.put(request.getId(), request);
        log.debug("Request {} has been added", requests.get(request.getId()));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeGameRequestsRegistry#get(java.lang.String)
     */
    @Override
    public ISweetSnakeGameRequest get(final String id) throws GameRequestNotFoundException {
        if (!contains(id)) {
            throw new GameRequestNotFoundException("request no more available");
        }
        return requests.get(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeGameRequestsRegistry#remove(java.lang.String)
     */
    @Override
    public void remove(final String id) throws GameRequestNotFoundException {
        if (!contains(id)) {
            throw new GameRequestNotFoundException("request no more available");
        }
        final ISweetSnakeGameRequest request = requests.get(id);
        request.getRequestingPlayer().removeSentRequestId(id);
        request.getRequestedPlayer().removeReceivedRequestId(id);
        request.cancel();
        log.debug("Request {} has been removed", requests.get(id));
        requests.remove(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeGameRequestsRegistry#getRequestsId()
     */
    @Override
    public List<String> getRequestsId() {
        final List<String> ids = new LinkedList<String>();
        for (final String id : requests.keySet()) {
            ids.add(id);
        }

        return ids;
    }

}
