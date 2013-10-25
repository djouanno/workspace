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
import fr.esir.project.sr.sweetsnake.commons.dto.PlayerDTO;
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

    private void startGameSession(final IPlayer player1, final IPlayer player2) {
        final ISweetSnakeServerGameSession gameSession = new SweetSnakeServerGameSession(player1, player2);
        gameSessions.add(gameSession);
        // TODO
        gameSession.startGame();
    }

    private String retrieveClientName(final ISweetSnakeClientCallback client) {
        try {
            return client.getName();
        } catch (final RemoteException e) {
            log.error(e.getMessage(), e);
        }
        return null;
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
        final String clientName = retrieveClientName(client);
        if (clientName == null) {
            throw new UnableToConnectException("username cannot be null");
        }
        if (players.containsKey(clientName)) {
            throw new UnableToConnectException("username " + clientName + " already taken");
        }
        final IPlayer player = new Player(client);
        player.setStatus(Status.AVAILABLE);
        players.put(clientName, player);
        log.info("New client with username {} has connected", clientName);
    }

    @Override
    public void disconnect(final ISweetSnakeClientCallback client) {
        final String clientName = retrieveClientName(client);
        if (players.containsKey(clientName)) {
            players.remove(clientName);
            log.info("Client with username {} has disconnected", clientName);
        }
    }

    @Override
    public void requestGameSession(final ISweetSnakeClientCallback client, final String otherPlayer) throws PlayerNotFoundException, UnableToMountGameSessionException {
        if (!players.containsKey(otherPlayer)) {
            throw new PlayerNotFoundException("unable to find the specified player");
        }
        final IPlayer player2 = retrievePlayer(otherPlayer);
        if (player2.getStatus() != Status.AVAILABLE) {
            throw new UnableToMountGameSessionException("player is not available");
        }

        final IPlayer player1 = retrievePlayer(retrieveClientName(client));
        if (pendingRequests.containsKey(player1)) {
            cancelGameSessionRequest(client);
        }

        final ISweetSnakeGameSessionRequest request = new SweetSnakeGameSessionRequest(player1.getName(), player2.getName());
        pendingRequests.put(player1, request);
        player1.setStatus(Status.PENDING);
        player2.getClientCallback().requestGame(request);
        log.info("Game session request between {} and {} is pending", player1.getName(), player2.getName());
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
        final String clientName = retrieveClientName(client);
        final IPlayer player = retrievePlayer(clientName);
        final ISweetSnakeGameSessionRequest request = retrieveRequest(player);
        if (request != null) {
            pendingRequests.remove(request);
            player.setStatus(Status.AVAILABLE);
        }
        log.info("Game session request canceled by player {}", player.getName());
    }

    @Override
    public void move(final Direction direction) {
        // TODO
    }

    @Override
    public List<PlayerDTO> getPlayersList(final ISweetSnakeClientCallback client) {
        final String clientName = retrieveClientName(client);
        final List<PlayerDTO> playersList = new ArrayList<PlayerDTO>();
        for (final String player : players.keySet()) {
            if (!clientName.equals(player)) {
                final PlayerDTO playerDTO = new PlayerDTO();
                playerDTO.setName(player);
                playerDTO.setStatus(players.get(player).getStatus());
                playersList.add(playerDTO);
            }
        }
        return playersList;
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    /**********************************************************************************************
     * [BLOCK] SETTERS
     **********************************************************************************************/

}
