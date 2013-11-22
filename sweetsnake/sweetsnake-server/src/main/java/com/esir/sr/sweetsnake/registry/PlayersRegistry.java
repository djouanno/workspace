package com.esir.sr.sweetsnake.registry;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.exception.PlayerNotFoundException;
import com.esir.sr.sweetsnake.session.Player;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
@Component
public class PlayersRegistry
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The logger */
    private static final Logger log = LoggerFactory.getLogger(PlayersRegistry.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The players map */
    private Map<String, Player> players;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     */
    protected PlayersRegistry() {
        super();
    }

    /**
     * 
     */
    @PostConstruct
    protected void init() {
        log.info("Initializing the players registry");
        players = new LinkedHashMap<String, Player>();
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * 
     * @param name
     * @return
     */
    public boolean contains(final String name) {
        return players.containsKey(name);
    }

    /**
     * 
     * @param player
     */
    public void add(final Player player) {
        players.put(player.getName(), player);
    }

    /**
     * 
     * @param name
     * @return
     * @throws PlayerNotFoundException
     */
    public Player get(final String name) throws PlayerNotFoundException {
        if (!contains(name)) {
            log.warn("Player with name {} was not found", name);
            throw new PlayerNotFoundException("player not found");
        }

        return players.get(name);
    }

    /**
     * 
     * @param name
     * @throws PlayerNotFoundException
     */
    public void remove(final String name) throws PlayerNotFoundException {
        if (!contains(name)) {
            log.warn("Player with name {} was not found", name);
            throw new PlayerNotFoundException("player not found");
        }

        final Player player = players.get(name);
        players.remove(name);
        log.debug("Player {} has been removed from registry", player.getName());
    }

    /**
     * 
     * @return
     */
    public Set<String> getPlayersName() {
        return Collections.unmodifiableSet(players.keySet());
    }

}
