package com.esir.sr.sweetsnake.server.rmi;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.commons.api.ISweetSnakeClientCallback;
import com.esir.sr.sweetsnake.commons.api.ISweetSnakeServer;
import com.esir.sr.sweetsnake.commons.dto.PlayerDTO;
import com.esir.sr.sweetsnake.commons.dto.SweetSnakeGameSessionDTO;
import com.esir.sr.sweetsnake.commons.dto.SweetSnakeGameSessionRequestDTO;
import com.esir.sr.sweetsnake.commons.enumerations.Direction;
import com.esir.sr.sweetsnake.commons.enumerations.Status;
import com.esir.sr.sweetsnake.commons.exceptions.PlayerNotFoundException;
import com.esir.sr.sweetsnake.commons.exceptions.UnableToConnectException;
import com.esir.sr.sweetsnake.commons.exceptions.UnableToMountGameSessionException;
import com.esir.sr.sweetsnake.server.api.ISweetSnakeGameSession;
import com.esir.sr.sweetsnake.server.api.ISweetSnakeGameSessionRequest;
import com.esir.sr.sweetsnake.server.api.ISweetSnakePlayer;
import com.esir.sr.sweetsnake.server.factory.SweetSnakeGameSessionFactory;
import com.esir.sr.sweetsnake.server.game.SweetSnakeGameSession;
import com.esir.sr.sweetsnake.server.game.SweetSnakeGameSessionRequest;
import com.esir.sr.sweetsnake.server.game.SweetSnakePlayer;

@Component
public class SweetSnakeServer implements ISweetSnakeServer
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final Logger                                   log = LoggerFactory.getLogger(SweetSnakeServer.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    private Map<String, ISweetSnakePlayer>                        players;
    private List<ISweetSnakeGameSession>                          gameSessions;
    private Map<ISweetSnakePlayer, ISweetSnakeGameSessionRequest> pendingRequests;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    protected SweetSnakeServer() {
        super();
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/

    private SweetSnakeGameSessionDTO startGameSession(final ISweetSnakePlayer player1, final ISweetSnakePlayer player2) {
        final ISweetSnakeGameSession gameSession = new SweetSnakeGameSession(player1, player2);
        gameSessions.add(gameSession);

        final SweetSnakeGameSessionDTO gameSessionDTO = SweetSnakeGameSessionFactory.convertGameSession(gameSession);

        player1.setStatus(Status.PLAYING);
        player2.setStatus(Status.PLAYING);
        gameSession.startGame();

        return gameSessionDTO;
    }

    private String retrieveClientName(final ISweetSnakeClientCallback client) {
        try {
            return client.getName();
        } catch (final RemoteException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    private ISweetSnakePlayer retrievePlayer(final String name) {
        if (players.containsKey(name)) {
            return players.get(name);
        }
        return null;
    }

    private ISweetSnakeGameSessionRequest retrieveRequest(final ISweetSnakePlayer player) {
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
        players = new HashMap<String, ISweetSnakePlayer>();
        gameSessions = new ArrayList<ISweetSnakeGameSession>();
        pendingRequests = new HashMap<ISweetSnakePlayer, ISweetSnakeGameSessionRequest>();
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
        final ISweetSnakePlayer player = new SweetSnakePlayer(client);
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
    public SweetSnakeGameSessionRequestDTO requestGameSession(final ISweetSnakeClientCallback client, final PlayerDTO otherPlayer) throws PlayerNotFoundException, UnableToMountGameSessionException {
        if (!players.containsKey(otherPlayer.getName())) {
            throw new PlayerNotFoundException("unable to find the specified player");
        }

        final ISweetSnakePlayer player2 = retrievePlayer(otherPlayer.getName());
        if (player2.getStatus() != Status.AVAILABLE) {
            throw new UnableToMountGameSessionException("player is not available");
        }

        final ISweetSnakePlayer player1 = retrievePlayer(retrieveClientName(client));
        if (pendingRequests.containsKey(player1)) {
            throw new UnableToMountGameSessionException("a request is already pending");
        }

        final ISweetSnakeGameSessionRequest request = new SweetSnakeGameSessionRequest(player1, player2);
        pendingRequests.put(player1, request);
        player1.setStatus(Status.PENDING);

        final SweetSnakeGameSessionRequestDTO requestDTO = SweetSnakeGameSessionFactory.convertGameSessionRequest(request);
        player2.getClientCallback().requestGame(requestDTO);
        log.info("Game session request between {} and {} is pending", player1.getName(), player2.getName());

        return requestDTO;
    }

    @Override
    public SweetSnakeGameSessionDTO acceptGameSession(final ISweetSnakeClientCallback client, final SweetSnakeGameSessionRequestDTO requestDTO) throws PlayerNotFoundException, UnableToMountGameSessionException {
        final ISweetSnakePlayer requestingPlayer = retrievePlayer(requestDTO.getRequestingPlayerName());
        if (requestingPlayer == null) {
            throw new PlayerNotFoundException("player not found");
        }

        final ISweetSnakeGameSessionRequest request = pendingRequests.get(requestingPlayer);
        if (request == null) {
            throw new UnableToMountGameSessionException("request not available");
        }
        if (!request.getRequestedPlayer().getName().equals(retrieveClientName(client))) {
            throw new UnableToMountGameSessionException("no matching request");
        }

        return startGameSession(request.getRequestingPlayer(), request.getRequestedPlayer());
    }

    @Override
    public void leaveGameSession(final ISweetSnakeClientCallback client) {
        // TODO
    }

    @Override
    public void cancelGameSessionRequest(final ISweetSnakeClientCallback client) {
        final String clientName = retrieveClientName(client);
        final ISweetSnakePlayer player = retrievePlayer(clientName);
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
                final PlayerDTO playerDTO = new PlayerDTO(player, players.get(player).getStatus());
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
