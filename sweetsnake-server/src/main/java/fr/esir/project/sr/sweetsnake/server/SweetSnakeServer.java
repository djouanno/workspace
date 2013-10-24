package fr.esir.project.sr.sweetsnake.server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import fr.esir.project.sr.sweetsnake.commons.api.ISweetSnakeClientCallback;
import fr.esir.project.sr.sweetsnake.commons.api.ISweetSnakeGameSessionRequest;
import fr.esir.project.sr.sweetsnake.commons.api.ISweetSnakeServer;
import fr.esir.project.sr.sweetsnake.commons.enumerations.Direction;
import fr.esir.project.sr.sweetsnake.commons.enumerations.Status;
import fr.esir.project.sr.sweetsnake.commons.exceptions.PlayerNotFoundException;
import fr.esir.project.sr.sweetsnake.commons.exceptions.UnableToConnectException;
import fr.esir.project.sr.sweetsnake.commons.exceptions.UnableToMountGameSessionException;
import fr.esir.project.sr.sweetsnake.server.api.IPlayer;
import fr.esir.project.sr.sweetsnake.server.api.ISweetSnakeServerGameSession;

@Component
public class SweetSnakeServer implements ISweetSnakeServer
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final Logger                         log = LoggerFactory.getLogger(SweetSnakeServer.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    private Map<String, IPlayer>                        players;
    private List<ISweetSnakeServerGameSession>          gameSessions;
    private Map<IPlayer, ISweetSnakeGameSessionRequest> pendingRequests;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    protected SweetSnakeServer() {
        super();
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/

    private void startGameSession(final IPlayer player1, final IPlayer player2) throws PlayerNotFoundException {
        final ISweetSnakeServerGameSession gameSession = new SweetSnakeServerGameSession(player1, player2);
        gameSessions.add(gameSession);
        // TODO
        gameSession.startGame();
    }

    private IPlayer retrievePlayer(final String name) {
        if (players.containsKey(name)) {
            return players.get(name);
        }
        return null;
    }

    private ISweetSnakeGameSessionRequest retrieveRequest(final IPlayer player) {
        if (pendingRequests.containsKey(player)) {
            return pendingRequests.get(player);
        }
        return null;
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    @PostConstruct
    public void init() {
        log.info("Initialization of the SweetSnakeServer");
        players = new HashMap<String, IPlayer>();
        gameSessions = new ArrayList<ISweetSnakeServerGameSession>();
        pendingRequests = new HashMap<IPlayer, ISweetSnakeGameSessionRequest>();
    }

    @Override
    public void connect(final ISweetSnakeClientCallback client) throws UnableToConnectException {
        try {
            if (client.getName() == null) {
                throw new UnableToConnectException("username cannot be null");
            }
            if (players.containsKey(client.getName())) {
                throw new UnableToConnectException("username " + client.getName() + " already taken");
            }
            players.put(client.getName(), new Player(client));
            log.info("New client with username : {} has connected", client.getName());
            log.info("Number of clients connected : {}", players.size());
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void disconnect(final ISweetSnakeClientCallback client) {
        try {
            if (players.containsKey(client.getName())) {
                players.remove(client.getName());
                log.info("Client with username : {} has disconnected", client.getName());
                log.info("Number of clients connected : {}", players.size());
            }
        } catch (final RemoteException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void requestGameSession(final ISweetSnakeClientCallback client, final String otherPlayer) throws PlayerNotFoundException, UnableToMountGameSessionException {
        if (!players.containsKey(otherPlayer)) {
            throw new PlayerNotFoundException("unable to find the specified player");
        }
        final IPlayer player = retrievePlayer(otherPlayer);
        if (player.getStatus() != Status.AVAILABLE) {
            throw new UnableToMountGameSessionException("player is not available");
        }
        final ISweetSnakeGameSessionRequest request = new SweetSnakeGameSessionRequest(player.getName(), otherPlayer);
        pendingRequests.put(player, request);
        player.setStatus(Status.PENDING);
        log.info("Game session request between {} and {} is pending", player.getName(), otherPlayer);
    }

    @Override
    public void acceptGameSession(final ISweetSnakeClientCallback client, final ISweetSnakeGameSessionRequest request) throws UnableToMountGameSessionException {
        if (!pendingRequests.containsValue(request)) {
            throw new UnableToMountGameSessionException("request timed out");
        }
        // TODO
    }

    @Override
    public void leaveGameSession(final ISweetSnakeClientCallback client) {
        // TODO
    }

    @Override
    public void cancelGameSessionRequest(final ISweetSnakeClientCallback client) {
        try {
            final IPlayer player = retrievePlayer(client.getName());
            final ISweetSnakeGameSessionRequest request = retrieveRequest(player);
            if (request != null) {
                pendingRequests.remove(request);
                player.setStatus(Status.AVAILABLE);
            }
            log.info("Game session request canceled by player {}", player.getName());
        } catch (final RemoteException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void move(final Direction direction) {
        // TODO
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    /**********************************************************************************************
     * [BLOCK] SETTERS
     **********************************************************************************************/

}
