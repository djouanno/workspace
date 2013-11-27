package com.esir.sr.sweetsnake.session;

import java.rmi.RemoteException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.api.IClientCallback;
import com.esir.sr.sweetsnake.api.IGameSessionCallback;
import com.esir.sr.sweetsnake.callback.GameSessionCallback;
import com.esir.sr.sweetsnake.constants.GameConstants;
import com.esir.sr.sweetsnake.constants.PropertiesConstants;
import com.esir.sr.sweetsnake.dto.GameSessionDTO;
import com.esir.sr.sweetsnake.dto.PlayerDTO;
import com.esir.sr.sweetsnake.enumeration.MoveDirection;
import com.esir.sr.sweetsnake.enumeration.PlayerStatus;
import com.esir.sr.sweetsnake.exception.GameSessionNotFoundException;
import com.esir.sr.sweetsnake.exception.MaximumNumberOfPlayersException;
import com.esir.sr.sweetsnake.exception.PlayerNotFoundException;
import com.esir.sr.sweetsnake.exception.UnauthorizedActionException;
import com.esir.sr.sweetsnake.factory.DtoConverterFactory;
import com.esir.sr.sweetsnake.game.engine.GameEngine;

/**
 * This class represents a game session between players.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
@Component
@Scope("prototype")
public class GameSession extends AbstractSession
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The logger */
    private static final Logger log = LoggerFactory.getLogger(GameSession.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The session id */
    private String              id;

    /** The players list */
    private List<Player>        players;

    /** The timelimit players mapping */
    private Map<Player, Long>   timeout;

    /** The game engine */
    private GameEngine          engine;

    /** Is the game started */
    private boolean             isStarted;

    /** The game session callback */
    private GameSessionCallback callback;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR & INIT
     **********************************************************************************************/

    /**
     * Creates a new game session
     */
    protected GameSession() {
        super();
    }

    /**
     * Initializes a new game session
     */
    @PostConstruct
    protected void init() {
        log.info("Initializing a new game session");
        id = RandomStringUtils.randomAlphanumeric(PropertiesConstants.GENERATED_ID_LENGTH);
        players = new LinkedList<Player>();
        timeout = new LinkedHashMap<Player, Long>();
        callback = beanProvider.getPrototype(GameSessionCallback.class, this);
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/

    /**
     * This method decreases the player's number of all the player which number is superior or equal to the starting index
     * 
     * @param startIndex
     *            The starting index
     */
    private void updatePlayersNumber(final int startIndex) {
        final ListIterator<Player> it = players.listIterator(startIndex);
        while (it.hasNext()) {
            final Player player = it.next();
            player.setNumber(player.getNumber() - 1);
        }
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC CALLBACK METHODS
     **********************************************************************************************/

    /**
     * This method is called by the client in order to ask for a game session to start
     * 
     * @param starterClient
     *            The client callback
     * @throws UnauthorizedActionException
     *             If the client is not authorized to start the session
     */
    public void startGame(final IClientCallback starterClient) throws UnauthorizedActionException {
        try {
            final Player starter = playersRegistry.get(starterClient.getUsername());

            if (starter.getNumber() != 1) {
                log.warn("Player {} tried to start session {} but was not authorized to", starter.getName(), id);
                throw new UnauthorizedActionException("unauthorized start");
            }

            for (final Player player : players) {
                player.setScore(0);
            }

            engine = new GameEngine(this);

            final GameSessionDTO sessionDto = DtoConverterFactory.convertGameSession(this);

            for (final Player player : players) {
                player.setStatus(PlayerStatus.PLAYING);
                player.getCallback().sessionStarted(sessionDto);
            }

            isStarted = true;
            engine.getGameBoard().clearRefreshes();

            server.sendRefreshPlayersList();
            server.sendRefreshSessionsList();

            log.info("Game session {} has been started", id);
        } catch (final PlayerNotFoundException | RemoteException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * This method is called by the client in order to ask for leaving a game session
     * 
     * @param leaverClient
     *            The client callback
     * @param fromDisconnect
     *            True if the player leaves the game due to a disconnection, false otherwise
     */
    public void leaveGame(final IClientCallback leaverClient, final boolean fromDisconnect) {
        try {
            final Player leaver = playersRegistry.get(leaverClient.getUsername());
            final PlayerDTO leaverDto = DtoConverterFactory.convertPlayer(leaver);

            log.debug("Player {} is leaving session {}", leaver.getName(), id);

            removePlayer(leaver);

            if (!fromDisconnect) {
                final GameSessionDTO sessionDto = DtoConverterFactory.convertGameSession(this);
                leaver.getCallback().sessionLeft(sessionDto, leaverDto, true, true);
            }

            if (isStarted) {
                engine.removeSnake(leaver);
            }

            final GameSessionDTO sessionDto = DtoConverterFactory.convertGameSession(this);
            final boolean stopped = players.size() <= 1, finished = players.isEmpty();

            for (final Player player : players) {
                player.getCallback().sessionLeft(sessionDto, leaverDto, stopped, finished);
                player.getCallback().refreshSession(sessionDto);
            }

            if (stopped) {
                stopGame(false);
            }

            if (finished) {
                sessionsRegistry.remove(id);
            }

            if (isStarted) {
                engine.getGameBoard().clearRefreshes();
            }

            server.sendRefreshPlayersList();
            server.sendRefreshSessionsList();

            log.info("Game session has been left by {}", leaver);
        } catch (final PlayerNotFoundException | GameSessionNotFoundException | RemoteException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * This method is called by the client in order to ask for a snake move in the game session
     * 
     * @param client
     *            The client callback
     * @param direction
     *            The direction where to move the snake
     */
    public void movePlayer(final IClientCallback client, final MoveDirection direction) {
        if (isStarted) {
            try {
                final Player player = playersRegistry.get(client.getUsername());

                if (System.currentTimeMillis() - timeout.get(player) > GameConstants.TIME_BETWEEN_2_MOVES) {
                    engine.moveSnake(direction, player);

                    final GameSessionDTO sessionDto = DtoConverterFactory.convertGameSession(this);
                    for (final Player _player : players) {
                        _player.getCallback().refreshSession(sessionDto);
                    }

                    engine.getGameBoard().clearRefreshes();
                    timeout.put(player, System.currentTimeMillis());
                }
            } catch (final PlayerNotFoundException | RemoteException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * This method adds a player to the game session
     * 
     * @param player
     *            The player to add
     * @throws MaximumNumberOfPlayersException
     *             If the game session is full
     */
    public void addPlayer(final Player player) throws MaximumNumberOfPlayersException {
        if (players.size() >= GameConstants.MAX_NUMBER_OF_PLAYERS) {
            log.warn("Player {} tried to join a full session", player.getName());
            throw new MaximumNumberOfPlayersException("session is full");
        }

        players.add(player);
        player.setNumber(players.size());
        player.setGameSessionId(id);
        player.setStatus(PlayerStatus.READY);

        timeout.put(player, 0L);

        final GameSessionDTO sessionDto = DtoConverterFactory.convertGameSession(this);

        if (!isStarted) {
            for (final Player _player : players) {
                try {
                    _player.getCallback().sessionJoined(_player.getNumber(), sessionDto);
                } catch (final RemoteException e) {
                    log.error(e.getMessage(), e);
                }
            }
        } else {
            try {
                player.getCallback().sessionJoined(player.getNumber(), sessionDto);
            } catch (final RemoteException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    /**
     * This method removes a player from the game session
     * 
     * @param player
     *            The player to remove
     */
    public void removePlayer(final Player player) {
        timeout.remove(player);
        players.remove(player);
        if (!isStarted) { // FIXME !!!!
            updatePlayersNumber(player.getNumber() - 1);
        }
        player.setStatus(PlayerStatus.AVAILABLE);
        player.setGameSessionId(null);
        player.setNumber(0);
        player.setScore(0);
    }

    /**
     * This methods checks whether a player is taking part in the game session or not
     * 
     * @param player
     *            The player to check
     * @return True if the player is taking part in the game session, false otherwise
     */
    public boolean contains(final Player player) {
        return players.contains(player);
    }

    /**
     * This method stops the current game session
     * 
     * @param fromAdmin
     *            True if the stop order comes from an administrator, false otherwise
     */
    public void stopGame(final boolean fromAdmin) {
        try {
            isStarted = false;

            if (!fromAdmin) {
                int maxScore = 0;
                final List<Player> winners = new LinkedList<Player>();
                for (final Player player : players) {
                    if (player.getScore() >= maxScore) {
                        maxScore = player.getScore();
                        winners.add(player);
                    }
                    player.setStatus(PlayerStatus.LOSER);
                }
                for (final Player player : winners) {
                    player.setStatus(PlayerStatus.WINNER);
                }
            } else {
                for (final Player player : players) {
                    player.setStatus(PlayerStatus.READY);
                }
            }

            final GameSessionDTO sessionDto = DtoConverterFactory.convertGameSession(this);

            for (final Player player : players) {
                player.getCallback().sessionFinished(sessionDto);
            }

            server.sendRefreshPlayersList();
            server.sendRefreshSessionsList();
            server.sendRefreshRequestsList();

            log.info("Game session {} has been finished", id);
        } catch (final RemoteException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * This method destroys the game session
     */
    public void destroy() {
        for (final Player player : players) {
            removePlayer(player);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "session[id=" + id + ", nbPlayers=" + players.size() + ", started=" + isStarted + "]";
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    /**
     * This method returns the id of the game session
     * 
     * @return A string representing the id of the game session
     */
    public String getId() {
        return id;
    }

    /**
     * This method returns all the players taking part in the game session
     * 
     * @return A list containing all the players taking part in the game session
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * This method returns the game engine associated with the game session
     * 
     * @return The game engine associated with the game session
     */
    public GameEngine getGameEngine() {
        return engine;
    }

    /**
     * This methods tells if the session is started or not
     * 
     * @return True if the session is started, false otherwise
     */
    public boolean isStarted() {
        return isStarted;
    }


    /**
     * This method returns the game session callback
     * 
     * @return The game session callback
     */
    public IGameSessionCallback getCallback() {
        return callback;
    }

}
