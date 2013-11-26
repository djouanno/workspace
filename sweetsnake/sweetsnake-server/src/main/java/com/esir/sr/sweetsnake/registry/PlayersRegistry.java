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
 * This class represents a players registry in order to store and managed them.
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
     * Creates a new players registry
     */
    protected PlayersRegistry() {
        super();
    }

    /**
     * Initializes a new players registry
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
     * This method returns whether the registry contains or not the player with the specified name
     * 
     * @param name
     *            The player name to look for
     * @return True if the registry contains the player, false otherwise
     */
    public boolean contains(final String name) {
        return players.containsKey(name);
    }

    /**
     * This method add a player to the registry
     * 
     * @param player
     *            The player to add
     */
    public void add(final Player player) {
        players.put(player.getName(), player);
    }

    /**
     * This method returns the player with the specified name from the registry
     * 
     * @param name
     *            The player name to look for
     * @return The player
     * @throws PlayerNotFoundException
     *             If the player was not found
     */
    public Player get(final String name) throws PlayerNotFoundException {
        if (!contains(name)) {
            log.warn("Player with name {} was not found", name);
            throw new PlayerNotFoundException("player not found");
        }

        return players.get(name);
    }

    /**
     * This method removes the player with the specified name from the registry
     * 
     * @param name
     *            The player name to remove
     * @throws PlayerNotFoundException
     *             If the player was not found
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
     * This method returns all the names of the stored players in the registry
     * 
     * @return A set containing the string of all the player names stored in the registry
     */
    public Set<String> getPlayersName() {
        return Collections.unmodifiableSet(players.keySet());
    }

}
