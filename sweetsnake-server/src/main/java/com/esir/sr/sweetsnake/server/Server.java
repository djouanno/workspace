package com.esir.sr.sweetsnake.server;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.api.IClientCallback;
import com.esir.sr.sweetsnake.api.IServer;
import com.esir.sr.sweetsnake.dto.GameRequestDTO;
import com.esir.sr.sweetsnake.dto.PlayerDTO;
import com.esir.sr.sweetsnake.enumeration.PlayerStatus;
import com.esir.sr.sweetsnake.exception.GameRequestNotFoundException;
import com.esir.sr.sweetsnake.exception.GameSessionNotFoundException;
import com.esir.sr.sweetsnake.exception.MaximumNumberOfPlayersException;
import com.esir.sr.sweetsnake.exception.PlayerNotAvailableException;
import com.esir.sr.sweetsnake.exception.PlayerNotFoundException;
import com.esir.sr.sweetsnake.exception.UnableToConnectException;
import com.esir.sr.sweetsnake.factory.DtoConverterFactory;
import com.esir.sr.sweetsnake.provider.BeanProvider;
import com.esir.sr.sweetsnake.registry.GameRequestsRegistry;
import com.esir.sr.sweetsnake.registry.GameSessionsRegistry;
import com.esir.sr.sweetsnake.registry.PlayersRegistry;
import com.esir.sr.sweetsnake.session.GameRequest;
import com.esir.sr.sweetsnake.session.GameSession;
import com.esir.sr.sweetsnake.session.Player;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
@Component
public class Server implements IServer
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The logger */
    private static final Logger  log = LoggerFactory.getLogger(Server.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The bean provider */
    @Autowired
    private BeanProvider         beanProvider;

    /** The players registry */
    @Autowired
    private PlayersRegistry      players;

    /** The requests registry */
    @Autowired
    private GameRequestsRegistry gameRequests;

    /** The sessions registry */
    @Autowired
    private GameSessionsRegistry gameSessions;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR & INIT
     **********************************************************************************************/

    /**
     * 
     */
    protected Server() {
        super();
    }

    /**
     * 
     */
    @PostConstruct
    protected void init() {
        log.info("Initialization of the SweetSnakeServer");
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/

    /**
     * 
     * @param client
     * @return
     */
    private String retrieveClientName(final IClientCallback client) {
        try {
            return client.getName();
        } catch (final RemoteException e) {
            log.error(e.getMessage(), e);
        }
        return new String();
    }


    /**
     * 
     * @param client
     * @return
     */
    private List<PlayerDTO> getPlayersList(final IClientCallback client) {
        final String clientName = retrieveClientName(client);
        final List<PlayerDTO> playersList = new LinkedList<PlayerDTO>();
        for (final String player : players.getPlayersName()) {
            if (!clientName.equals(player)) {
                try {
                    final PlayerDTO playerDTO = DtoConverterFactory.convertPlayer(players.get(player));
                    playersList.add(playerDTO);
                } catch (final PlayerNotFoundException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return playersList;
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC IMPLEMENTED METHODS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IServer#connect(com.esir.sr.sweetsnake.api.IClientCallback)
     */
    @Override
    public void connect(final IClientCallback client) throws UnableToConnectException {
        final String clientName = retrieveClientName(client);

        // bad username
        if (clientName == null) {
            throw new UnableToConnectException("username cannot be null");
        }

        // username already taken
        if (players.contains(clientName)) {
            throw new UnableToConnectException("username " + clientName + " already taken");
        }

        // creating player
        final Player player = beanProvider.getPrototype(Player.class, client);
        players.add(player);

        sendRefreshPlayersList();

        log.info("New client with username {} has connected", clientName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IServer#disconnect(com.esir.sr.sweetsnake.api.IClientCallback)
     */
    @Override
    public void disconnect(final IClientCallback client) {
        try {
            final String clientName = retrieveClientName(client);
            final Player player = players.get(clientName);

            // cancel sent requests
            for (final String requestId : player.getSentRequestsIds()) {
                final GameRequest request = gameRequests.get(requestId);
                request.cancel();
                gameRequests.remove(requestId);
            }

            // deny received requests
            if (player.getReceivedRequestId() != null) {
                final GameRequest request = gameRequests.get(player.getReceivedRequestId());
                final GameSession gameSession = gameSessions.get(request.getSessionid());
                request.deny(gameSession.allDenied());
                gameRequests.remove(request.getId());
            }

            // leave current session
            if (player.getGameSessionId() != null) {
                gameSessions.get(player.getGameSessionId()).leaveGame(client, true);
            }

            // removing player from registry
            players.remove(clientName);

            sendRefreshPlayersList();

            log.info("Player {} has disconnected", clientName);
        } catch (final PlayerNotFoundException | GameRequestNotFoundException | GameSessionNotFoundException e) {
            log.error(e.getMessage(), e);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IServer#sendRequest(com.esir.sr.sweetsnake.api.IClientCallback,
     * com.esir.sr.sweetsnake.dto.PlayerDTO)
     */
    @Override
    public void sendRequest(final IClientCallback client, final PlayerDTO otherPlayer) throws PlayerNotFoundException, PlayerNotAvailableException {
        final Player player1 = players.get(retrieveClientName(client)), player2 = players.get(otherPlayer.getName());

        // player is not available to play
        if (player2.getStatus() != PlayerStatus.AVAILABLE) {
            throw new PlayerNotAvailableException("player is not available");
        }

        GameSession gameSession;
        try {
            // session does not exist
            if (player1.getGameSessionId() == null) {
                log.debug("Game session is null, creating new one");
                gameSession = beanProvider.getPrototype(GameSession.class);
                gameSession.addPlayer(player1);
                gameSession.addFictivePlayer(player2);
                gameSessions.add(gameSession);
            }
            // session already exists
            else {
                log.debug("Game session already exists, adding fictive player {}", player2.getName());
                gameSession = gameSessions.get(player1.getGameSessionId());
                gameSession.addFictivePlayer(player2);
            }

            // creating request
            final GameRequest request = beanProvider.getPrototype(GameRequest.class, gameSession.getId(), player1, player2);
            gameRequests.add(request);
        } catch (final MaximumNumberOfPlayersException | GameSessionNotFoundException e) {
            log.error(e.getMessage(), e);
        }

        sendRefreshPlayersList();

        log.info("Game request between {} and {} is pending", player1.getName(), player2.getName());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IServer#acceptRequest(com.esir.sr.sweetsnake.api.IClientCallback,
     * com.esir.sr.sweetsnake.dto.GameRequestDTO)
     */
    @Override
    public void acceptRequest(final IClientCallback client, final GameRequestDTO requestDTO) throws GameRequestNotFoundException, GameSessionNotFoundException, MaximumNumberOfPlayersException {
        final GameRequest request = gameRequests.get(requestDTO.getId());

        // request no more available
        if (!request.getRequestedPlayer().getName().equals(retrieveClientName(client))) {
            throw new GameRequestNotFoundException("no matching request");
        }

        // removing request
        gameRequests.remove(requestDTO.getId());

        // retrieving session & player
        final GameSession gameSession = gameSessions.get(requestDTO.getSessionId());
        final Player requestedPlayer = request.getRequestedPlayer();
        gameSession.addPlayer(requestedPlayer);

        sendRefreshPlayersList();

        log.info("Player {} joined the session {}", requestedPlayer.getName(), gameSession.getId());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IServer#denyRequest(com.esir.sr.sweetsnake.api.IClientCallback,
     * com.esir.sr.sweetsnake.dto.GameRequestDTO)
     */
    @Override
    public void denyRequest(final IClientCallback client, final GameRequestDTO requestDTO) throws GameRequestNotFoundException {
        final GameRequest request = gameRequests.get(requestDTO.getId());
        final Player requestedPlayer = request.getRequestedPlayer();

        // request no more available
        if (!requestedPlayer.getName().equals(retrieveClientName(client))) {
            throw new GameRequestNotFoundException("no matching request");
        }

        boolean allDenied = false;

        // deny session and check session status
        if (gameSessions.contains(request.getSessionid())) {
            try {
                final GameSession gameSession = gameSessions.get(request.getSessionid());
                gameSession.denied(requestedPlayer);
                allDenied = gameSession.allDenied();
            } catch (final GameSessionNotFoundException e) {
                log.error(e.getMessage(), e);
            }
        }

        // deny and remove request
        request.deny(allDenied);
        gameRequests.remove(requestDTO.getId());

        // remove session if everyone denied
        if (allDenied) {
            try {
                gameSessions.remove(requestDTO.getSessionId());
            } catch (final GameSessionNotFoundException e) {
                log.error(e.getMessage(), e);
            }
        }

        sendRefreshPlayersList();

        log.info("Request {} denied by {}", request.getId(), request.getRequestedPlayer().getName());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IServer#cancelRequest(com.esir.sr.sweetsnake.api.IClientCallback,
     * com.esir.sr.sweetsnake.dto.GameRequestDTO)
     */
    @Override
    public void cancelRequest(final IClientCallback client, final GameRequestDTO requestDTO) throws GameRequestNotFoundException {
        final GameRequest request = gameRequests.get(requestDTO.getId());

        // cancel and remove the request
        request.cancel();
        gameRequests.remove(request.getId());

        sendRefreshPlayersList();

        log.info("Request {} has been canceled by {}", request.getId(), request.getRequestingPlayer().getName());
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
    *
    */
    public void sendRefreshPlayersList() {
        // wait for everything to be properly updated
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                for (final String playerName : players.getPlayersName()) {
                    try {
                        final Player player = players.get(playerName);
                        final IClientCallback callback = player.getCallback();
                        callback.refreshPlayersList(getPlayersList(callback));
                    } catch (RemoteException | PlayerNotFoundException e) {
                        log.error(e.getMessage(), e);
                    }
                }
            }
        }, 200);
    }

}
