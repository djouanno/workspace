package com.esir.sr.sweetsnake.session;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.LoggerFactory;

import com.esir.sr.sweetsnake.constants.GameConstants;
import com.esir.sr.sweetsnake.constants.PropertiesConstants;
import com.esir.sr.sweetsnake.dto.GameSessionDTO;
import com.esir.sr.sweetsnake.dto.PlayerDTO;
import com.esir.sr.sweetsnake.enumeration.MoveDirection;
import com.esir.sr.sweetsnake.exception.MaximumNumberOfPlayersException;
import com.esir.sr.sweetsnake.factory.DtoConverterFactory;
import com.esir.sr.sweetsnake.game.engine.GameEngine;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class GameSession
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The logger */
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(GameSession.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The session id */
    private final String                  id;

    /** The players list */
    private final List<Player>            players;

    /** The game engine */
    private GameEngine                    engine;

    /** The number of players */
    private int                           nbPlayers;

    /** Is the game started */
    private boolean                       isGameStarted;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     */
    public GameSession() {
        log.info("Initializing a new game session");
        id = RandomStringUtils.randomAlphanumeric(PropertiesConstants.GENERATED_ID_LENGTH);
        players = new LinkedList<Player>();
    }

    /**
     * 
     * @param _player1
     * @param _player2
     */
    public GameSession(final List<Player> _players) {
        this();
        for (final Player player : _players) {
            player.setNumber(++nbPlayers);
            players.add(player);
        }
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * 
     * @param player
     * @throws MaximumNumberOfPlayersException
     */
    public void addPlayer(final Player player) throws MaximumNumberOfPlayersException {
        if (players.size() >= GameConstants.MAX_NUMBER_OF_PLAYERS) {
            throw new MaximumNumberOfPlayersException("maximum number of players reached");
        }
        players.add(player);
    }

    /**
     * 
     * @param player
     * @return
     */
    public boolean contains(final Player player) {
        return players.contains(player);
    }

    /**
     * 
     */
    public void startGame() {
        engine = new GameEngine(this);
        try {
            final GameSessionDTO sessionDto = DtoConverterFactory.convertGameSession(this);
            for (final Player player : players) {
                player.getClientCallback().gameStarted(sessionDto);
            }
            isGameStarted = true;
            engine.getGameBoard().clearRefreshes();
            log.info("Game session has been started");
        } catch (final RemoteException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 
     */
    public void leaveGame(final Player leaver) {
        try {
            final GameSessionDTO sessionDto = DtoConverterFactory.convertGameSession(this);
            final PlayerDTO leaverDto = DtoConverterFactory.convertPlayer(leaver);
            for (final Player player : players) {
                player.getClientCallback().gameLeaved(sessionDto, leaverDto);
            }
            leaver.setGameSessionId(null);
            leaver.setNumber(0);
            leaver.setScore(0);
            players.remove(leaver);
            isGameStarted = false;
            log.info("Game session has been stopped");
        } catch (final RemoteException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 
     * @param player
     * @param direction
     */
    public void movePlayer(final Player _player, final MoveDirection direction) {
        if (isGameStarted) {
            try {
                engine.moveSnake(direction, _player);
                final GameSessionDTO sessionDto = DtoConverterFactory.convertGameSession(this);
                for (final Player player : players) {
                    player.getClientCallback().refreshGame(sessionDto);
                }
                engine.getGameBoard().clearRefreshes();
            } catch (final RemoteException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 
     * @return
     */
    public boolean isGameStarted() {
        return isGameStarted;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "session[id=" + id + ", nbPlayers=" + nbPlayers + ", started=" + isGameStarted + "]";
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    /**
     * 
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @return
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * 
     * @return
     */
    public GameEngine getGameEngine() {
        return engine;
    }

}
