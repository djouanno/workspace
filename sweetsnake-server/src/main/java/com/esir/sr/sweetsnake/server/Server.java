package com.esir.sr.sweetsnake.server;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.api.IClientCallback;
import com.esir.sr.sweetsnake.api.IServer;
import com.esir.sr.sweetsnake.api.IServerAdmin;
import com.esir.sr.sweetsnake.api.IServerGui;
import com.esir.sr.sweetsnake.dto.GameRequestDTO;
import com.esir.sr.sweetsnake.dto.GameSessionDTO;
import com.esir.sr.sweetsnake.dto.PlayerDTO;
import com.esir.sr.sweetsnake.enumeration.PlayerStatus;
import com.esir.sr.sweetsnake.exception.GameRequestNotFoundException;
import com.esir.sr.sweetsnake.exception.GameSessionNotFoundException;
import com.esir.sr.sweetsnake.exception.MaximumNumberOfPlayersException;
import com.esir.sr.sweetsnake.exception.PlayerNotAvailableException;
import com.esir.sr.sweetsnake.exception.PlayerNotFoundException;
import com.esir.sr.sweetsnake.exception.UnableToConnectException;
import com.esir.sr.sweetsnake.exception.UnauthorizedActionException;
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
public class Server implements IServer, IServerAdmin
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
    private PlayersRegistry      playersRegistry;

    /** The requests registry */
    @Autowired
    private GameRequestsRegistry requestsRegistry;

    /** The sessions registry */
    @Autowired
    private GameSessionsRegistry sessionsRegistry;

    /** The server GUI */
    @Autowired
    private IServerGui           gui;

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
        log.info("Initializating the Server");
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                gui.serverStarted();
            }
        }, 200);
    }

    /**
     * 
     */
    @PreDestroy
    protected void destroy() {
        log.info("Destroying the Server");
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
            return client.getName().trim();
        } catch (final RemoteException e) {
            log.error(e.getMessage(), e);
        }
        return new String();
    }

    /**
     * 
     * @param name
     * @return
     */
    private boolean validClientName(final String name) {
        return name.matches("^\\w+$");
    }

    /**
     * 
     * @param client
     * @return
     */
    private List<PlayerDTO> getPlayersList(final IClientCallback client) {
        final String clientName = client == null ? "" : retrieveClientName(client);
        final List<PlayerDTO> playersList = new LinkedList<PlayerDTO>();
        for (final String player : playersRegistry.getPlayersName()) {
            if (!clientName.equals(player)) {
                try {
                    final PlayerDTO playerDTO = DtoConverterFactory.convertPlayer(playersRegistry.get(player));
                    playersList.add(playerDTO);
                } catch (final PlayerNotFoundException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return playersList;
    }

    /**
     * 
     * @return
     */
    private List<GameRequestDTO> getRequestsList() {
        final List<GameRequestDTO> requestsList = new LinkedList<GameRequestDTO>();
        for (final String id : requestsRegistry.getRequestsId()) {
            try {
                final GameRequestDTO requestDto = DtoConverterFactory.convertGameRequest(requestsRegistry.get(id));
                requestsList.add(requestDto);
            } catch (final GameRequestNotFoundException e) {
                log.error(e.getMessage(), e);
            }
        }
        return requestsList;
    }

    /**
     * 
     * @return
     */
    private List<GameSessionDTO> getSessionsList() {
        final List<GameSessionDTO> sessionsList = new LinkedList<GameSessionDTO>();
        for (final String id : sessionsRegistry.getSessionsId()) {
            try {
                final GameSessionDTO sessionDto = DtoConverterFactory.convertGameSession(sessionsRegistry.get(id));
                sessionsList.add(sessionDto);
            } catch (final GameSessionNotFoundException e) {
                log.error(e.getMessage(), e);
            }
        }
        return sessionsList;
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC SERVER IMPLEMENTED METHODS
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
        if (clientName == null || !validClientName(clientName)) {
            log.warn("Invalid username {}, must be alphanumeric only", clientName);
            throw new UnableToConnectException("invalid username, must be alphanumeric only");
        }

        // username already taken
        if (playersRegistry.contains(clientName)) {
            log.warn("Username {} is already taken", clientName);
            throw new UnableToConnectException("username " + clientName + " is already taken");
        }

        // creating player
        final Player player = beanProvider.getPrototype(Player.class, client);
        playersRegistry.add(player);

        sendRefreshPlayersList();
        sendRefreshSessionsList();

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
            final Player player = playersRegistry.get(clientName);

            // cancel sent requests
            for (final String requestId : player.getSentRequestsIds()) {
                final GameRequest request = requestsRegistry.get(requestId);
                request.cancel();
                requestsRegistry.remove(requestId);
            }

            // deny received requests
            if (player.getReceivedRequestId() != null) {
                final GameRequest request = requestsRegistry.get(player.getReceivedRequestId());
                request.deny();
                requestsRegistry.remove(request.getId());
            }

            // leave current session
            if (player.getGameSessionId() != null) {
                sessionsRegistry.get(player.getGameSessionId()).leaveGame(client, true);
            }

            // removing player from registry
            playersRegistry.remove(clientName);

            sendRefreshPlayersList();
            sendRefreshSessionsList();
            sendRefreshRequestsList();

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
    public void sendRequest(final IClientCallback client, final PlayerDTO playerDto) throws PlayerNotFoundException, PlayerNotAvailableException, GameSessionNotFoundException {
        final Player player1 = playersRegistry.get(retrieveClientName(client)), player2 = playersRegistry.get(playerDto.getName());

        // player is not available to play
        if (player2.getStatus() != PlayerStatus.AVAILABLE) {
            throw new PlayerNotAvailableException("player is not available");
        }

        final GameSession gameSession = sessionsRegistry.get(player1.getGameSessionId());
        gameSession.addFictivePlayer(player2);

        // creating request
        final GameRequest request = beanProvider.getPrototype(GameRequest.class, gameSession.getId(), player1, player2);
        requestsRegistry.add(request);

        sendRefreshPlayersList();
        sendRefreshSessionsList();
        sendRefreshRequestsList();

        log.info("Game request between {} and {} is pending", player1.getName(), player2.getName());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IServer#acceptRequest(com.esir.sr.sweetsnake.api.IClientCallback,
     * com.esir.sr.sweetsnake.dto.GameRequestDTO)
     */
    @Override
    public void acceptRequest(final IClientCallback client, final GameRequestDTO requestDto) throws GameRequestNotFoundException, GameSessionNotFoundException, MaximumNumberOfPlayersException {
        final GameRequest request = requestsRegistry.get(requestDto.getId());

        // request no more available
        if (!request.getRequestedPlayer().getName().equals(retrieveClientName(client))) {
            log.warn("No matching request with id {}", request.getId());
            throw new GameRequestNotFoundException("no matching request");
        }

        // removing request
        requestsRegistry.remove(requestDto.getId());

        // retrieving session & player
        final GameSession gameSession = sessionsRegistry.get(requestDto.getSessionId());
        final Player requestedPlayer = request.getRequestedPlayer();
        gameSession.addPlayer(requestedPlayer);

        sendRefreshPlayersList();
        sendRefreshSessionsList();
        sendRefreshRequestsList();

        log.info("Player {} joined the session {}", requestedPlayer.getName(), gameSession.getId());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IServer#denyRequest(com.esir.sr.sweetsnake.api.IClientCallback,
     * com.esir.sr.sweetsnake.dto.GameRequestDTO)
     */
    @Override
    public void denyRequest(final IClientCallback client, final GameRequestDTO requestDto) throws GameRequestNotFoundException {
        final GameRequest request = requestsRegistry.get(requestDto.getId());
        final Player requestedPlayer = request.getRequestedPlayer();

        // request no more available
        if (!requestedPlayer.getName().equals(retrieveClientName(client))) {
            log.warn("No matching request with id {}", request.getId());
            throw new GameRequestNotFoundException("no matching request");
        }

        // deny session and check session status
        if (sessionsRegistry.contains(request.getSessionid())) {
            try {
                final GameSession gameSession = sessionsRegistry.get(request.getSessionid());
                gameSession.denied(requestedPlayer);
            } catch (final GameSessionNotFoundException e) {
                log.error(e.getMessage(), e);
            }
        }

        // deny and remove request
        request.deny();
        requestsRegistry.remove(requestDto.getId());

        sendRefreshPlayersList();
        sendRefreshSessionsList();
        sendRefreshRequestsList();

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
        final GameRequest request = requestsRegistry.get(requestDTO.getId());

        // cancel and remove the request
        request.cancel();
        requestsRegistry.remove(request.getId());

        sendRefreshPlayersList();
        sendRefreshSessionsList();
        sendRefreshRequestsList();

        log.info("Request {} has been canceled by {}", request.getId(), request.getRequestingPlayer().getName());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IServer#createSession(com.esir.sr.sweetsnake.api.IClientCallback)
     */
    @Override
    public void createSession(final IClientCallback client) throws UnauthorizedActionException {
        try {
            final Player player = playersRegistry.get(retrieveClientName(client));

            if (player.getGameSessionId() != null) {
                log.warn("Player {} tried to create a game session while already in another one", player.getName());
                throw new UnauthorizedActionException("you already are in a game session");
            }

            final GameSession gameSession = beanProvider.getPrototype(GameSession.class);

            try {
                gameSession.addPlayer(player);
            } catch (final MaximumNumberOfPlayersException e) {
                log.error(e.getMessage(), e);
            }

            sessionsRegistry.add(gameSession);

            sendRefreshPlayersList();
            sendRefreshSessionsList();

            log.info("Game session {} has been created by {}", gameSession.getId(), player.getName());
        } catch (final PlayerNotFoundException e) {
            log.error(e.getMessage(), e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IServer#joinSession(com.esir.sr.sweetsnake.api.IClientCallback,
     * com.esir.sr.sweetsnake.dto.GameSessionDTO)
     */
    @Override
    public void joinSession(final IClientCallback client, final GameSessionDTO sessionDTO) throws GameSessionNotFoundException, MaximumNumberOfPlayersException {
        final GameSession session = sessionsRegistry.get(sessionDTO.getId());
        try {
            final Player player = playersRegistry.get(retrieveClientName(client));
            session.addPlayer(player);

            sendRefreshPlayersList();
            sendRefreshSessionsList();

            log.info("Session {} has been joined by {}", session.getId(), player.getName());
        } catch (final PlayerNotFoundException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC SERVER ADMIN IMPLEMENTED METHODS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IServerAdmin#startServer()
     */
    @Override
    public void startServer() {
        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IServerAdmin#stopServer()
     */
    @Override
    public void stopServer() {
        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IServerAdmin#kickPlayer(com.esir.sr.sweetsnake.dto.PlayerDTO)
     */
    @Override
    public void kickPlayer(final PlayerDTO player) {
        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IServerAdmin#stopSession(com.esir.sr.sweetsnake.dto.GameSessionDTO)
     */
    @Override
    public void stopSession(final GameSessionDTO session) {
        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IServerAdmin#removeSession(com.esir.sr.sweetsnake.dto.GameSessionDTO)
     */
    @Override
    public void removeSession(final GameSessionDTO session) {
        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IServerAdmin#removeRequest(com.esir.sr.sweetsnake.dto.GameRequestDTO)
     */
    @Override
    public void removeRequest(final GameRequestDTO request) {
        // TODO Auto-generated method stub
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
    *
    */
    public void sendRefreshPlayersList() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                for (final String playerName : playersRegistry.getPlayersName()) {
                    try {
                        final Player player = playersRegistry.get(playerName);
                        final IClientCallback callback = player.getCallback();
                        callback.refreshPlayersList(getPlayersList(callback));
                    } catch (RemoteException | PlayerNotFoundException e) {
                        log.error(e.getMessage(), e);
                    }
                }
                gui.refreshPlayers(getPlayersList(null));
            }
        }, 100);
    }

    /**
     * 
     */
    public void sendRefreshSessionsList() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                final List<GameSessionDTO> sessionsList = getSessionsList();
                for (final String playerName : playersRegistry.getPlayersName()) {
                    try {
                        final Player player = playersRegistry.get(playerName);
                        player.getCallback().refreshSessionsList(sessionsList);
                    } catch (RemoteException | PlayerNotFoundException e) {
                        log.error(e.getMessage(), e);
                    }
                }
                gui.refreshSessions(sessionsList);
            }
        }, 100);
    }

    /**
     * 
     */
    public void sendRefreshRequestsList() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                final List<GameRequestDTO> requestsList = getRequestsList();
                gui.refreshRequests(requestsList);
            }
        }, 100);
    }

}
