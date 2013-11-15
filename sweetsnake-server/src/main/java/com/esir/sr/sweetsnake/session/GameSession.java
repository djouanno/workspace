package com.esir.sr.sweetsnake.session;

import java.rmi.RemoteException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
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

    /** The fictive players mapping */
    private Map<String, Player> fictivePlayers;

    /** The game engine */
    private GameEngine          engine;

    /** Is the game started */
    private boolean             isGameStarted;

    /** The game session callback */
    private GameSessionCallback callback;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR & INIT
     **********************************************************************************************/

    /**
     * 
     */
    protected GameSession() {
        super();
    }

    /**
     * 
     * @throws RemoteException
     */
    @PostConstruct
    protected void init() {
        log.info("Initializing a new game session");
        id = RandomStringUtils.randomAlphanumeric(PropertiesConstants.GENERATED_ID_LENGTH);
        players = new LinkedList<Player>();
        fictivePlayers = new LinkedHashMap<String, Player>();
        callback = beanProvider.getPrototype(GameSessionCallback.class, this);
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * 
     * @param player
     */
    public void denied(final Player player) {
        final Player fictivePlayer = fictivePlayers.get(player.getName());
        fictivePlayer.setStatus(PlayerStatus.DENIED);
        for (final Player _player : getPlayers()) {
            if (!_player.isFictive()) {
                try {
                    _player.getCallback().sessionJoined(DtoConverterFactory.convertGameSession(this));
                } catch (final RemoteException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    /**
     * 
     * @return
     */
    public boolean allDenied() {
        for (final Player player : players) {
            if (player.getNumber() != 1) {
                if (player.getStatus() != PlayerStatus.DENIED) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 
     * @param player
     * @throws MaximumNumberOfPlayersException
     */
    public void addPlayer(final Player player) throws MaximumNumberOfPlayersException {
        Player fictivePlayer = null;
        if (fictivePlayers.containsKey(player.getName())) {
            fictivePlayer = fictivePlayers.get(player.getName());
            players.remove(fictivePlayer);
            fictivePlayers.remove(player.getName());
        }

        if (players.size() >= GameConstants.MAX_NUMBER_OF_PLAYERS) {
            throw new MaximumNumberOfPlayersException("maximum number of players reached");
        }

        player.setNumber(fictivePlayer == null ? 1 : fictivePlayer.getNumber());
        players.add(player.getNumber() - 1, player);
        player.setGameSessionId(id);
        player.setStatus(PlayerStatus.PRESENT);

        final GameSessionDTO sessionDto = DtoConverterFactory.convertGameSession(this);

        for (final Player _player : getPlayers()) {
            if (!_player.isFictive()) {
                try {
                    _player.getCallback().sessionJoined(sessionDto);
                } catch (final RemoteException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    /**
     * 
     * @param player
     */
    public void addFictivePlayer(final Player player) {
        final Player fictivePlayer = beanProvider.getPrototype(Player.class, player.getName());
        fictivePlayers.put(fictivePlayer.getName(), fictivePlayer);

        players.add(fictivePlayer);
        fictivePlayer.setStatus(PlayerStatus.INVITED);
        fictivePlayer.setNumber(players.size());

        try {
            players.get(0).getCallback().sessionJoined(DtoConverterFactory.convertGameSession(this));
        } catch (final RemoteException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 
     */
    public void removeAllFictivePlayers() {
        for (final String playerName : fictivePlayers.keySet()) {
            players.remove(fictivePlayers.get(playerName));
        }
        fictivePlayers.clear();
    }

    /**
     * 
     * @param player
     */
    public void removePlayer(final Player player) {
        player.setStatus(PlayerStatus.AVAILABLE);
        player.setGameSessionId(null);
        player.setNumber(0);
        player.setScore(0);
        players.remove(player);
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
        return "session[id=" + id + ", nbPlayers=" + players.size() + ", started=" + isGameStarted + "]";
    }

    /**********************************************************************************************
     * [BLOCK] CALLBACK METHODS
     **********************************************************************************************/

    /**
     * 
     * @param starterClient
     * @throws UnauthorizedActionException
     */
    public void startGame(final IClientCallback starterClient) throws UnauthorizedActionException {
        try {
            final Player starter = playersRegistry.get(starterClient.getName());

            if (starter.getNumber() != 1) {
                throw new UnauthorizedActionException("unauthorized start");
            }

            removeAllFictivePlayers();

            engine = new GameEngine(this);
            final GameSessionDTO sessionDto = DtoConverterFactory.convertGameSession(this);
            for (final Player player : players) {
                player.getCallback().sessionStarted(sessionDto);
            }
            isGameStarted = true;
            engine.getGameBoard().clearRefreshes();
            log.info("Game session {} has been started", id);
        } catch (final PlayerNotFoundException | RemoteException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 
     */
    public void leaveGame(final IClientCallback leaverClient, final boolean fromDisconnect) {
        try {
            final Player leaver = playersRegistry.get(leaverClient.getName());
            final PlayerDTO leaverDto = DtoConverterFactory.convertPlayer(leaver);

            log.debug("Player {} is leaving session {}", leaver.getName(), id);

            removePlayer(leaver);

            if (!fromDisconnect) {
                final GameSessionDTO sessionDto = DtoConverterFactory.convertGameSession(this);
                leaver.getCallback().sessionLeft(sessionDto, leaverDto);
            }

            if (isGameStarted) {
                engine.removeSnake(leaver);
            }

            final GameSessionDTO sessionDto = DtoConverterFactory.convertGameSession(this);

            for (final Player player : players) {
                log.debug("Telling {} that {} has left the session", player.getName(), leaver.getName());
                player.getCallback().sessionLeft(sessionDto, leaverDto);
                player.getCallback().refreshSession(sessionDto);
            }

            if (players.size() <= 1) {
                sessionsRegistry.remove(id);
            } else {
                if (isGameStarted) {
                    engine.getGameBoard().clearRefreshes();
                }
            }

            log.info("Game session has been left by {}", leaver);
        } catch (final PlayerNotFoundException | GameSessionNotFoundException | RemoteException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 
     * @param player
     * @param direction
     */
    public void movePlayer(final IClientCallback client, final MoveDirection direction) {
        if (isGameStarted) {
            try {
                final Player player = playersRegistry.get(client.getName());
                engine.moveSnake(direction, player);
                final GameSessionDTO sessionDto = DtoConverterFactory.convertGameSession(this);
                for (final Player _player : players) {
                    log.debug("Telling {} to refresh the session", _player.getName());
                    _player.getCallback().refreshSession(sessionDto);
                }
                engine.getGameBoard().clearRefreshes();
            } catch (final PlayerNotFoundException | RemoteException e) {
                log.error(e.getMessage(), e);
            }
        }
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

    /**
     * 
     * @return
     */
    public IGameSessionCallback getCallback() {
        return callback;
    }

}
