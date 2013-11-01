package com.esir.sr.sweetsnake.registry;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.api.ISweetSnakePlayer;
import com.esir.sr.sweetsnake.api.ISweetSnakePlayersRegistry;
import com.esir.sr.sweetsnake.exception.PlayerNotFoundException;

@Component
public class SweetSnakePlayersRegistry implements ISweetSnakePlayersRegistry
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final org.slf4j.Logger  log = LoggerFactory.getLogger(SweetSnakePlayersRegistry.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    private Map<String, ISweetSnakePlayer> players;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     */
    public SweetSnakePlayersRegistry() {
        super();
    }

    /**
     * 
     */
    @PostConstruct
    public void init() {
        log.info("Initializing players registry");
        players = new LinkedHashMap<String, ISweetSnakePlayer>();
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakePlayersRegistry#contains(java.lang.String)
     */
    @Override
    public boolean contains(final String name) {
        return players.containsKey(name);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakePlayersRegistry#add(com.esir.sr.sweetsnake.api.
     * ISweetSnakePlayer)
     */
    @Override
    public void add(final ISweetSnakePlayer player) {
        players.put(player.getName(), player);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakePlayersRegistry#get(java.lang.String)
     */
    @Override
    public ISweetSnakePlayer get(final String name) throws PlayerNotFoundException {
        if (!contains(name)) {
            throw new PlayerNotFoundException("player not found");
        }
        return players.get(name);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakePlayersRegistry#remove(java.lang.String)
     */
    @Override
    public void remove(final String name) throws PlayerNotFoundException {
        if (!contains(name)) {
            throw new PlayerNotFoundException("player not found");
        }
        players.remove(name);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakePlayersRegistry#getPlayersName()
     */
    @Override
    public List<String> getPlayersName() {
        final List<String> names = new LinkedList<String>();
        for (final String name : players.keySet()) {
            names.add(name);
        }

        return names;
    }

}
